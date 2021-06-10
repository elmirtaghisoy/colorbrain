package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    List<TrainingEntity> findAllByStatusTrue();

    List<TrainingEntity> findAllByStatusFalse();

    @Transactional
    @Modifying
    @Query(
            "update TrainingEntity t set " +
                    "t.context = :#{#training.context}," +
                    "t.coverPath = :#{#training.coverPath}," +
                    "t.header = :#{#training.header}," +
                    "t.lastRegistrationDay = :#{#training.lastRegistrationDay}," +
                    "t.registrationIsActive = :#{#training.registrationIsActive}," +
                    "t.startDate = :#{#training.startDate}," +
                    "t.startTime = :#{#training.startTime}," +
                    "t.status = :#{#training.staus}," +
                    "t.trainerName = :#{#training.trainerName}," +
                    "t.trainingPrice = :#{#training.trainingPrice}," +
                    "t.updatedAt = :#{#training.updatedAt} " +
                    "where t.id = :#{#training.id}"
    )
    void update(TrainingEntity training);

    @Query(
            nativeQuery = true,
            value = "select t.status from training t where t.id =:id limit 1"
    )
    String getTrainingStatusById(Long id);

}
