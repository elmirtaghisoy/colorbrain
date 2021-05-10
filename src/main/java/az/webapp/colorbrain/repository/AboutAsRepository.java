package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.AboutAsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutAsRepository extends JpaRepository<AboutAsEntity,Long> {
    List<AboutAsEntity> findAll();
}
