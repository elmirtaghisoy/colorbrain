package az.webapp.colorbrain.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    //  bunu enum olaraq deyishdire bilerik ????
    @Column(name = "file_type")
    private int fileType;

    @Column(name = "file_path")
    private String filePath;
    //  bunu enum olaraq deyishdire bilerik ????
    @Column(name = "file_category")
    private int fileCategory;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private TrainingEntity trainingEntity;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private NewsEntity newsEntity;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity projectEntity;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private BlogEntity blogEntity;

}
