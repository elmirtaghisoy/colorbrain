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
    List<GetView.File.Training> findAllByTrainingId(Long id);

//    List<FileEntity> findAllByNewsEntity_IdOrderByFileTypeAsc(Long id);

    List<GetView.File.Media> findAllByMediaId(Long id);

    DeleteFileView getFileById(Long id);

//    List<FileEntity> findAllByProjectEntity_IdOrderByFileTypeAsc(Long id);

//    List<FileEntity> findAllByBlogEntity_IdOrderByFileTypeAsc(Long id);

//    FileEntity findFirstByTrainingEntityId(Long id);

//    FileEntity findFirstByNewsEntityId(Long id);

    @Query(
            nativeQuery = true,
            value = "select f.folder_uuid from file f where f.media_id =:id limit 1"
    )
    String getFolderUUIDForMedia(Long id);

    @Query(
            nativeQuery = true,
            value = "select f.folder_uuid from file f where f.training_id =:id limit 1"
    )
    String getFolderUUIDForTraining(Long id);

    @Transactional
    @Modifying
    @Query(
            value = "delete from FileEntity f where f.id=:id"
    )
    void deleteFile(Long id);


//    FileEntity findFirstByProjectEntityId(Long id);

//    FileEntity findFirstByBlogEntityId(Long id);

}
