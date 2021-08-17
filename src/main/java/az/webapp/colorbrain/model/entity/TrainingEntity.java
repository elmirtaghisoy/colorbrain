package az.webapp.colorbrain.model.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "training")
@Data
public class TrainingEntity extends AuditableV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "header")
    private String header;

    @Column(name = "context")
    private String context;

    @Column(name = "cover_path")
    private String coverPath;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "trainer_name")
    private String trainerName;

    @Column(name = "training_price")
    private String trainingPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ref_object_id")
    private List<FileEntity> fileEntities;

    @Column(name = "folder_uuid")
    private String folderUuid;

    @Transient
    private MultipartFile cover;

}
