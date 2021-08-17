package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.TrainingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long>, JpaSpecificationExecutor<TrainingEntity> {

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
                    "t.status = :#{#training.status}," +
                    "t.trainerName = :#{#training.trainerName}," +
                    "t.trainingPrice = :#{#training.trainingPrice}," +
                    "t.updatedAt = :#{#training.updatedAt}," +
                    "t.startRegistrationDay =:#{#training.startRegistrationDay}," +
                    "t.active = :#{#training.active}," +
                    "t.registrationIsActive = :#{#training.registrationIsActive}," +
                    "t.status = :#{#training.status} " +
                    "where t.id = :#{#training.id}"
    )
    void update(TrainingEntity training);

    @Query(
            nativeQuery = true,
            value = "select t.status from training t where t.id =:id limit 1"
    )
    String getTrainingStatusById(Long id);

    @Query(
            nativeQuery = true,
            value = "select p.folder_uuid project p where p.id =:id limit 1"
    )
    String getFolderUUID(Long id);

    Page<TrainingEntity> findAll(Specification<TrainingEntity> specification, Pageable pageable);
}
