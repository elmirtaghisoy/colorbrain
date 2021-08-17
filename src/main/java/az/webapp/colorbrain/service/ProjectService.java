package az.webapp.colorbrain.service;

import az.webapp.colorbrain.component.CustomFile;
import az.webapp.colorbrain.component.criteria.ProjectSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.component.paging.Paging;
import az.webapp.colorbrain.component.query.SearchQueries;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.ProjectRepository;
import az.webapp.colorbrain.repository.view.DeleteFileView;
import az.webapp.colorbrain.repository.view.GetView;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static az.webapp.colorbrain.config.ApplicationConstants.MEDIA;
import static az.webapp.colorbrain.config.ApplicationConstants.PROJECT;
import static az.webapp.colorbrain.config.ApplicationConstants.PROJECT_DEFAULT_IMG_PATH;
import static az.webapp.colorbrain.config.MvcConfig.uploadPath;
import static az.webapp.colorbrain.util.CommonUtils.generateRandomFolderName;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final FileRepository fileRepository;

    public Paged<ProjectEntity> getAllActiveProject(int page, int size) {
        var request = PageRequest.of(page - 1, size);
        Page<ProjectEntity> postPage = projectRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), page, size));
    }

    public ProjectEntity getOneProjectById(Long id) {
        return projectRepository.getOne(id);
    }

    public void saveProject(ProjectEntity projectEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = generateRandomFolderName();
        projectEntity.setFolderUuid(uuidFolderName);
        if (projectEntity.getCover().isEmpty()) {
            projectEntity.setCoverPath(PROJECT_DEFAULT_IMG_PATH);
        } else {
            CustomFile file = CustomFile.builder()
                    .category(PROJECT)
                    .folder(uuidFolderName)
                    .file(projectEntity.getCover())
                    .build();
            projectEntity.setCoverPath(FileService.saveSingle(file));
        }
        if (!files.get(0).isEmpty()) {
            projectEntity.setFileEntities(FileService.saveMultiple(PROJECT, uuidFolderName, files));
        }
        projectRepository.save(projectEntity);
    }

    public void updateProject(ProjectEntity projectEntity) throws IOException {
        var projectEntityFromDb = getOneProjectById(projectEntity.getId());
        projectEntity.setCreatedAt(projectEntityFromDb.getCreatedAt());
        projectEntity.setUpdatedAt(LocalDateTime.now());
        if (projectEntity.getCover().isEmpty()) {
            projectEntity.setCoverPath(projectEntityFromDb.getCoverPath());
        } else {
            String folderUUID = projectRepository.getFolderUUID(projectEntity.getId());

            CustomFile file = CustomFile.builder()
                    .category(MEDIA)
                    .folder(folderUUID)
                    .file(projectEntity.getCover())
                    .build();

            projectEntity.setCoverPath(FileService.saveSingle(file));
        }
        projectRepository.update(projectEntity);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<GetView.File> getAllFilesByProjectId(Long id) {
        return fileRepository.findAllByFileCategoryAndRefObjectId(PROJECT, id);
    }

    public boolean getProjectStatus(Long id) {
        return Boolean.parseBoolean(projectRepository.getProjectStatusById(id));
    }

    public void saveAdditionalProjectFiles(List<MultipartFile> files, Long id) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> fileList = FileService.saveMultiple(PROJECT, projectRepository.getFolderUUID(id), files);
            fileList.forEach(file -> file.setRefObjectId(id));
            fileRepository.saveAll(fileList);
        }
    }

    public void deleteProjectFileByFileId(Long fileId) {
        DeleteFileView file = fileRepository.getFileById(fileId);
        fileRepository.deleteByFileCategoryAndRefObjectId(PROJECT, file.getId());
        FileService.deleteFile(uploadPath + "/" + file.getFilePath());
    }

    public Paged<ProjectEntity> searchAllProject(int page, int size, ProjectSearchCriteria searchRequest) {
        Pageable pageRequest = PageRequest.of(page - 1, size);

        Page<ProjectEntity> postPage = projectRepository.findAll(
                SearchQueries.createProjectSpecification(searchRequest),
                pageRequest
        );

        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), page, size));
    }
}

