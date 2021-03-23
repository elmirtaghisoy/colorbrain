package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void createTraining(TrainingEntity trainingEntity) {
        trainingRepository.save(trainingEntity);
    }

    public void updateTraining(TrainingEntity trainingEntity) {
        trainingRepository.save(trainingEntity);
    }

    public void deleteTraining(TrainingEntity trainingEntity) {
        trainingRepository.delete(trainingEntity);
    }
}
