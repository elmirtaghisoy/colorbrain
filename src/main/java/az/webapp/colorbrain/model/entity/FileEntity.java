package az.webapp.colorbrain.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "training_id")
    private TrainingEntity trainingEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "news_id")
    private NewsEntity newsEntity;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "blog_id")
    private BlogEntity blogEntity;

}
