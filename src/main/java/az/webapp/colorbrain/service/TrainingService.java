package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.CustomFile;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.TrainingRepository;
import az.webapp.colorbrain.repository.view.DeleteFileView;
import az.webapp.colorbrain.repository.view.GetView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static az.webapp.colorbrain.config.ApplicationConstants.TRAINING;
import static az.webapp.colorbrain.config.MvcConfig.uploadPath;
import static az.webapp.colorbrain.util.CommonUtils.getRandomFolderName;

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

    public TrainingEntity getOneTrainingById(Long id) {
        return trainingRepository.getOne(id);
    }

    public void updateTraining(TrainingEntity trainingEntity) throws IOException {
        TrainingEntity trainingEntityFromDB = getOneTrainingById(trainingEntity.getId());
        trainingEntity.setCreatedAt(trainingEntityFromDB.getCreatedAt());
        trainingEntity.setUpdatedAt(trainingEntityFromDB.getUpdatedAt());

        if (trainingEntity.getCoverImage().isEmpty()) {
            trainingEntity.setCoverPath(trainingEntityFromDB.getCoverPath());
        } else {
            String folderUUID = fileRepository.getFolderUUIDForTraining(trainingEntity.getId());
            CustomFile file = CustomFile.builder()
                    .category(TRAINING)
                    .folder(folderUUID)
                    .file(trainingEntity.getCoverImage())
                    .build();
            trainingEntity.setCoverPath(FileService.saveSingle(file));
        }
        trainingRepository.update(trainingEntity);
    }

    public void saveTraining(TrainingEntity trainingEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = getRandomFolderName();
        CustomFile file = CustomFile.builder()
                .category(TRAINING)
                .folder(uuidFolderName)
                .file(trainingEntity.getCoverImage())
                .build();
        trainingEntity.setCoverPath(FileService.saveSingle(file));
        trainingEntity.setFileEntities(FileService.saveMultiple(TRAINING, uuidFolderName, files));
        trainingRepository.save(trainingEntity);
    }

    public void deleteTraining(Long trainingId) {
        trainingRepository.deleteById(trainingId);
    }

    public List<GetView.File.Training> getAllFilesByTrainingId(Long id) {
        return fileRepository.findAllByTrainingId(id);
    }

    public boolean getTrainingStatus(Long id) {
        return Boolean.parseBoolean(trainingRepository.getTrainingStatusById(id));
    }

    public void saveAdditionalTrainingFiles(List<MultipartFile> files, Long trainingId) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> fileList = FileService.saveMultiple(TRAINING, fileRepository.getFolderUUIDForTraining(trainingId), files);
            fileList.forEach(file -> file.setTrainingId(trainingId));
            fileRepository.saveAll(fileList);
        }
    }

    public void deleteFileByTrainingId(Long fileId) {
        DeleteFileView file = fileRepository.getFileById(fileId);
        fileRepository.deleteFile(file.getId());
        FileService.deleteFile(uploadPath + "/" + file.getFilePath());
    }

}
