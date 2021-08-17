package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.BlogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    List<BlogEntity> findAllByActiveTrue();

    @Transactional
    @Modifying
    @Query(
            "update BlogEntity b set " +
                    "b.context = :#{#blog.context}," +
                    "b.coverPath = :#{#blog.coverPath}," +
                    "b.header = :#{#blog.header}," +
                    "b.updatedAt = :#{#blog.updatedAt}, " +
                    "b.categoryEntity = :#{#blog.categoryEntity} " +
                    "where b.id = :#{#blog.id}"
    )
    void update(BlogEntity blog);

    @Transactional
    @Modifying
    @Query("update BlogEntity b set b.categoryEntity.id = 1 where b.categoryEntity.id = :#{#id}")
    void deleteCategoryFromBlogs(Long id);

    @Query(
            nativeQuery = true,
            value = "select b.folder_uuid from blog b where b.id =:id limit 1"
    )
    String getFolderUUID(Long id);


    @EntityGraph(attributePaths = "categoryEntity")
    Page<BlogEntity> findAll(Specification<BlogEntity> blogSpecification, Pageable pageRequest);
}
