package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<TrainingEntity> getAllActiveTraining() {
        return trainingRepository.findAllByStatusTrue();
    }

    public List<TrainingEntity> getAllFinishedTraining() {
        return trainingRepository.findAllByStatusFalse();
    }

    public void saveTraining(TrainingEntity trainingEntity, MultipartFile file, List<MultipartFile> files) throws IOException {
        trainingEntity.setFileEntities(FileService.saveMultiple(files, "training"));
        trainingEntity.setImageCover(FileService.saveSingle(file));
        trainingEntity.setCreatedAt(LocalDateTime.now());
        trainingEntity.setStatus(true);
        trainingRepository.save(trainingEntity);
    }

    public void saveAdditionalTrainingFiles(List<MultipartFile> files, TrainingEntity trainingEntity) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> savedFiles = FileService.saveMultiple(files, "training");
            for (FileEntity file : savedFiles) {
                file.setTrainingEntity(trainingEntity);
                fileRepository.save(file);
            }
        }
    }

    public void updateTraining(TrainingEntity trainingEntity) {
        TrainingEntity trainingEntityFromDb = getOneTrainingById(trainingEntity.getId());
        if (!trainingEntity.isStatus()) {
            trainingEntity.setRegistrationIsActive(false);
        }
        trainingEntity.setCreatedAt(trainingEntityFromDb.getCreatedAt());
        trainingEntity.setUpdatedAt(LocalDateTime.now());
        trainingRepository.save(trainingEntity);
    }

    public void deleteTraining(TrainingEntity trainingEntity) {
        trainingRepository.delete(trainingEntity);
    }

    public TrainingEntity getOneTrainingById(Long id) {
        return trainingRepository.getOne(id);
    }

    public List<FileEntity> getAllFilesByTrainingId(Long id) {
        return fileRepository.findAllByTrainingEntity_IdOrderByFileTypeAsc(id);
    }

    public void deleteFileByTrainingId(FileEntity fileEntity) {
        fileRepository.delete(fileEntity);
    }
}
