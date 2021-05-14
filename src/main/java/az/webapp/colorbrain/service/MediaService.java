package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.MediaEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
        mediaEntity.setCoverPath(FileService.saveSingle(mediaEntity.getCoverImage()));
        mediaEntity.setFileEntities(FileService.saveMultiple(files, "media"));
        mediaEntity.setCreatedAt(LocalDateTime.now());
        mediaEntity.setActive(true);
        mediaRepository.save(mediaEntity);
    }

    public MediaEntity getOneMediaById(Long id) {
        return mediaRepository.getOne(id);
    }

    public void updateMedia(MediaEntity mediaEntity) {
        MediaEntity mediaEntityFromDb = getOneMediaById(mediaEntity.getId());
        mediaEntity.setCreatedAt(mediaEntityFromDb.getCreatedAt());
        mediaEntity.setUpdatedAt(LocalDateTime.now());
        mediaRepository.save(mediaEntity);
    }

    public void deleteMedia(MediaEntity mediaEntity) {
        mediaRepository.delete(mediaEntity);
    }

    public List<FileEntity> getAllFilesByMediaId(Long id) {
        return fileRepository.findAllByMediaEntity_IdOrderByFileTypeAsc(id);
    }

    public void saveAdditionalMediaFiles(List<MultipartFile> files, MediaEntity mediaEntity) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> savedFiles = FileService.saveMultiple(files, "media");
            for (FileEntity file : savedFiles) {
                file.setMediaEntity(mediaEntity);
                fileRepository.save(file);
            }
        }
    }

    public void deleteFileByTrainingId(FileEntity fileEntity) {
        fileRepository.delete(fileEntity);
    }
}
