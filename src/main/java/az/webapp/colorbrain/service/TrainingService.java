package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    public List<TrainingEntity> getAllActiveTraining() {
        return trainingRepository.findAllByStatusTrue();
    }

    public List<TrainingEntity> getAllFinishedTraining() {
        return trainingRepository.findAllByStatusFalse();
    }

    public void saveTraining(TrainingEntity trainingEntity) {
        trainingEntity.setCreatedAt(LocalDateTime.now());
        trainingEntity.setStatus(true);
        trainingRepository.save(trainingEntity);
    }

    public void updateTraining(TrainingEntity trainingEntity) {
        TrainingEntity trainingEntityFromDb = getOneTrainingById(trainingEntity.getId());
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
}
