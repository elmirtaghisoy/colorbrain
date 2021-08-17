package az.webapp.colorbrain.model.entity;

import az.webapp.colorbrain.component.annotation.IsImage;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "project")
@Data
public class ProjectEntity extends AuditableV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Layihənin adını daxil edin.")
    @Column(name = "header")
    private String header;

    @NotBlank(message = "Layihə haqqında məlumat daxil edin.")
    @Column(name = "context")
    private String context;

    @Column(name = "cover_path")
    private String coverPath;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_object_id")
    private List<FileEntity> fileEntities;

    @Column(name = "folder_uuid")
    private String folderUuid;

    @Transient
    @IsImage(message = "Əlavə etdiyiniz faylın formatı ancaq (JPG, JPEG, IMG, PNG) ola bilər.")
    private MultipartFile cover;

}
