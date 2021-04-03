package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity,Long> {
}
