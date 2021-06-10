package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.CustomFile;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static az.webapp.colorbrain.config.MvcConfig.uploadPath;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<ProjectEntity> getAllActiveProject() {
        return projectRepository.findAllByStatusTrue();
    }

    public List<ProjectEntity> getAllFinishedProject() {
        return projectRepository.findAllByStatusFalse();
    }

    public void saveProject(ProjectEntity projectEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = UUID.randomUUID().toString();
//        CustomFile file = new CustomFile("project", uuidFolderName, projectEntity.getCoverImage());
//        projectEntity.setCoverPath(FileService.saveSingle(file));
//        projectEntity.setFileEntities(FileService.saveMultiple(files, "project", uuidFolderName));
        projectEntity.setCreatedAt(LocalDateTime.now());
        projectEntity.setStatus(true);
        projectEntity.setActive(true);
        projectRepository.save(projectEntity);
    }

    public void saveAdditionalProjectFiles(List<MultipartFile> files, ProjectEntity projectEntity) throws IOException {
        if (files.get(0).getSize() != 0) {
//            FileEntity fileEntityFromDb = fileRepository.findFirstByProjectEntityId(projectEntity.getId());
//            List<FileEntity> savedFiles = FileService.saveMultiple(files, "project", fileEntityFromDb.getFolderUuid());
//            for (FileEntity file : savedFiles) {
//                file.setProjectEntity(projectEntity);
//                fileRepository.save(file);
//            }
        }
    }

    public void updateProject(ProjectEntity projectEntity) {
        ProjectEntity projectEntityFromDb = getOneProjectById(projectEntity.getId());
        projectEntity.setCreatedAt(projectEntityFromDb.getCreatedAt());
        projectEntity.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(projectEntity);
    }

    public void deleteProject(ProjectEntity projectEntity) {
        projectRepository.delete(projectEntity);
    }

    public ProjectEntity getOneProjectById(Long id) {
        return projectRepository.getOne(id);
    }

    public List<FileEntity> getAllFilesByProjectId(Long id) {
//        return fileRepository.findAllByProjectEntity_IdOrderByFileTypeAsc(id);
        return null;

    }

    public void deleteFileByProjectId(FileEntity fileEntity) {
        fileRepository.delete(fileEntity);
        FileService.deleteFile(uploadPath + "/" + fileEntity.getFilePath());
    }
}

