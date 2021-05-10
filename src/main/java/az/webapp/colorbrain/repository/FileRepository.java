package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAllByTrainingEntity_IdOrderByFileTypeAsc(Long id);

    List<FileEntity> findAllByNewsEntity_IdOrderByFileTypeAsc(Long id);

    List<FileEntity> findAllByMediaEntity_IdOrderByFileTypeAsc(Long id);

    List<FileEntity> findAllByProjectEntity_IdOrderByFileTypeAsc(Long id);

    List<FileEntity> findAllByBlogEntity_IdOrderByFileTypeAsc(Long id);
}
