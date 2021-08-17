package az.webapp.colorbrain.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "file")
@Data
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_type")
    private int fileType;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_category")
    private String fileCategory;

    @Column(name = "ref_object_id")
    private Long refObjectId;

}
