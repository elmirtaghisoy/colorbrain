package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {


    @Query(
            nativeQuery = true,
            value = "select t.status from project t where t.id =:id limit 1"
    )
    String getProjectStatusById(Long id);

    @Transactional
    @Modifying
    @Query(
            "update ProjectEntity p set " +
                    "p.context = :#{#project.context}," +
                    "p.coverPath = :#{#project.coverPath}," +
                    "p.header = :#{#project.header}," +
                    "p.startDate = :#{#project.startDate}," +
                    "p.lastRegistrationDay = :#{#project.lastRegistrationDay}," +
                    "p.updatedAt = :#{#project.updatedAt}," +
                    "p.status = :#{#project.status}," +
                    "p.startRegistrationDay =:#{#project.startRegistrationDay}," +
                    "p.registrationIsActive = :#{#project.registrationIsActive}," +
                    "p.active =:#{#project.active} " +
                    "where p.id = :#{#project.id}"
    )
    void update(ProjectEntity project);

    @Query(
            nativeQuery = true,
            value = "select p.folder_uuid project p where p.id =:id limit 1"
    )
    String getFolderUUID(Long id);

    Page<ProjectEntity> findAllByStatus(boolean status, Pageable pageable);

    Page<ProjectEntity> findAll(Specification<ProjectEntity> projectSpecification, Pageable pageRequest);
}
