package az.webapp.colorbrain.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "file")
@Getter
@Setter
@ToString
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
    private int fileCategory;

    @Column(name = "folder_uuid")
    private String folderUuid;

    //    @ManyToOne(cascade = CascadeType.PERSIST)
    @Column(name = "training_id")
    private Long trainingId;

    //    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "news_id")
    private Long newsId;

    //    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "teamMembers_id")
    private Long teamMemberId;

    //    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "project_id")
    private Long projectId;

    //    @ManyToOne(cascade = CascadeType.ALL)
    @Column(name = "blog_id")
//    private BlogEntity blogEntity;
    private Long blogId;

    @Column(name = "media_id")
    private Long mediaId;
}
