package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.repository.view.DeleteFileView;
import az.webapp.colorbrain.repository.view.GetView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<GetView.File> findAllByFileCategoryAndRefObjectId(String category, Long id);

    @Transactional
    @Modifying
    @Query(
            value = "delete from FileEntity f where f.id=:id and f.fileCategory=:category"
    )
    void deleteByFileCategoryAndRefObjectId(String category, Long id);

    DeleteFileView getFileById(Long fileId);

}
