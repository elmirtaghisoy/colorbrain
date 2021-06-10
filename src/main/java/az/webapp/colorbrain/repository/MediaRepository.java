package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Long> {

    @Transactional
    @Modifying
    @Query(
            "update MediaEntity m set " +
                    "m.coverPath = :#{#media.coverPath}," +
                    "m.updatedAt =:#{#media.updatedAt}," +
                    "m.header = :#{#media.header} " +
                    "where m.id = :#{#media.id}"
    )
    void update(MediaEntity media);

}


