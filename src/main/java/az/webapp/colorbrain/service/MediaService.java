package az.webapp.colorbrain.service;

import az.webapp.colorbrain.component.CustomFile;
import az.webapp.colorbrain.component.criteria.MediaSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.component.paging.Paging;
import az.webapp.colorbrain.component.query.SearchQueries;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.MediaEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.MediaRepository;
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
import static az.webapp.colorbrain.config.ApplicationConstants.MEDIA_DEFAULT_IMG_PATH;
import static az.webapp.colorbrain.config.MvcConfig.uploadPath;
import static az.webapp.colorbrain.util.CommonUtils.generateRandomFolderName;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final FileRepository fileRepository;

    public Paged<MediaEntity> getAllMedia(int pageNumber, int size) {
        var request = PageRequest.of(pageNumber - 1, size);
        Page<MediaEntity> postPage = mediaRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public void saveMedia(MediaEntity mediaEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = generateRandomFolderName();
        mediaEntity.setFolderUuid(uuidFolderName);
        if (mediaEntity.getCover().isEmpty()) {
            mediaEntity.setCoverPath(MEDIA_DEFAULT_IMG_PATH);
        } else {
            CustomFile file = CustomFile.builder()
                    .category(MEDIA)
                    .folder(uuidFolderName)
                    .file(mediaEntity.getCover())
                    .build();
            mediaEntity.setCoverPath(FileService.saveSingle(file));
        }
        if (!files.get(0).isEmpty()) {
            mediaEntity.setFileEntities(FileService.saveMultiple(MEDIA, uuidFolderName, files));
        }
        mediaRepository.save(mediaEntity);
    }

    public MediaEntity getOneMediaById(Long id) {
        return mediaRepository.getOne(id);
    }

    public void updateMedia(MediaEntity mediaEntity) throws IOException {
        var mediaEntityFromDb = getOneMediaById(mediaEntity.getId());
        mediaEntity.setCreatedAt(mediaEntityFromDb.getCreatedAt());
        mediaEntity.setUpdatedAt(LocalDateTime.now());
        if (mediaEntity.getCover().isEmpty()) {
            mediaEntity.setCoverPath(mediaEntityFromDb.getCoverPath());
        } else {
            String folderUUID = mediaRepository.getFolderUUID(mediaEntity.getId());
            CustomFile file = CustomFile.builder()
                    .category(MEDIA)
                    .folder(folderUUID)
                    .file(mediaEntity.getCover())
                    .build();
            mediaEntity.setCoverPath(FileService.saveSingle(file));
        }
        mediaRepository.update(mediaEntity);
    }

    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
    }

    public List<GetView.File> getAllFilesByMediaId(Long id) {
        return fileRepository.findAllByFileCategoryAndRefObjectId(MEDIA, id);
    }

    public void saveAdditionalMediaFiles(List<MultipartFile> files, Long id) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> fileList = FileService.saveMultiple(MEDIA, mediaRepository.getFolderUUID(id), files);
            fileList.forEach(file -> file.setRefObjectId(id));
            fileRepository.saveAll(fileList);
        }
    }

    public void deleteMediaFileByFileId(Long fileId) {
        DeleteFileView file = fileRepository.getFileById(fileId);
        fileRepository.deleteByFileCategoryAndRefObjectId(MEDIA, file.getId());
        FileService.deleteFile(uploadPath + file.getFilePath().replace("/file", ""));
    }

    public Paged<MediaEntity> searchAllMedia(int page, int size, MediaSearchCriteria searchRequest) {
        Pageable pageRequest = PageRequest.of(page - 1, size);

        Page<MediaEntity> postPage = mediaRepository.findAll(
                SearchQueries.createMediaSpecification(searchRequest),
                pageRequest
        );

        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), page, size));
    }
}
