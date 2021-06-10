package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.CustomFile;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.MediaEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.MediaRepository;
import az.webapp.colorbrain.repository.view.DeleteFileView;
import az.webapp.colorbrain.repository.view.GetView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static az.webapp.colorbrain.config.ApplicationConstants.MEDIA;
import static az.webapp.colorbrain.config.MvcConfig.uploadPath;
import static az.webapp.colorbrain.util.CommonUtils.getRandomFolderName;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<MediaEntity> getAllMedia() {
        return mediaRepository.findAll();
    }

    public void saveMedia(MediaEntity mediaEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = getRandomFolderName();
        CustomFile file = CustomFile.builder()
                .category(MEDIA)
                .folder(uuidFolderName)
                .file(mediaEntity.getCoverImage())
                .build();

        mediaEntity.setCoverPath(FileService.saveSingle(file));
        mediaEntity.setFileEntities(FileService.saveMultiple(MEDIA, uuidFolderName, files));
        mediaRepository.save(mediaEntity);
    }

    public MediaEntity getOneMediaById(Long id) {
        return mediaRepository.getOne(id);
    }

    public void updateMedia(MediaEntity mediaEntity) throws IOException {
        MediaEntity mediaEntityFromDb = getOneMediaById(mediaEntity.getId());
        mediaEntity.setCreatedAt(mediaEntityFromDb.getCreatedAt());
        mediaEntity.setUpdatedAt(LocalDateTime.now());
        if (mediaEntity.getCoverImage().isEmpty()) {
            mediaEntity.setCoverPath(mediaEntityFromDb.getCoverPath());
        } else {
            String folderUUID = fileRepository.getFolderUUIDForMedia(mediaEntity.getId());

            CustomFile file = CustomFile.builder()
                    .category(MEDIA)
                    .folder(folderUUID)
                    .file(mediaEntity.getCoverImage())
                    .build();

            mediaEntity.setCoverPath(FileService.saveSingle(file));
        }
        mediaRepository.update(mediaEntity);
    }

    public void deleteMedia(Long mediaId) {
        mediaRepository.deleteById(mediaId);
    }

    public List<GetView.File.Media> getAllFilesByMediaId(Long id) {
        return fileRepository.findAllByMediaId(id);
    }

    public void saveAdditionalMediaFiles(List<MultipartFile> files, Long mediaId) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> fileList = FileService.saveMultiple(MEDIA, fileRepository.getFolderUUIDForMedia(mediaId), files);
            fileList.forEach(file -> file.setMediaId(mediaId));
            fileRepository.saveAll(fileList);
        }
    }

    public void deleteFileMediaFile(Long fileId) {
        DeleteFileView file = fileRepository.getFileById(fileId);
        fileRepository.deleteFile(file.getId());
        FileService.deleteFile(uploadPath + "/" + file.getFilePath());
    }
}
