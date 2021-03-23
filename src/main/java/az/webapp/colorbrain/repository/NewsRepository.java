package az.webapp.colorbrain.repository;


import az.webapp.colorbrain.model.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    List<NewsEntity> findAllByStatusTrue();
    List<NewsEntity> findAllByStatusFalse();
}
