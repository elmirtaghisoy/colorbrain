package az.webapp.colorbrain.repository;


import az.webapp.colorbrain.model.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    List<NewsEntity> findAll();

    @Transactional
    @Modifying
    @Query(
            "update NewsEntity n set " +
                    "n.context = :#{#news.context}," +
                    "n.coverPath = :#{#news.coverPath}," +
                    "n.header = :#{#news.header}," +
                    "n.updatedAt = :#{#news.updatedAt} " +
                    "where n.id = :#{#news.id}"
    )
    void update(NewsEntity news);

    @Query(
            nativeQuery = true,
            value = "select n.folder_uuid news n where n.id =:id limit 1"
    )
    String getFolderUUID(Long id);

    Page<NewsEntity> findAll(Specification<NewsEntity> newsSpecification, Pageable pageRequest);
}
