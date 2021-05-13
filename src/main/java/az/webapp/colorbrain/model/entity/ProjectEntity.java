package az.webapp.colorbrain.model.entity;

import az.webapp.colorbrain.component.annotation.IsImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@ToString
public class ProjectEntity {

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "cover_path")
    private String coverPath;

    @NotNull(message = "Layihənin başlayacağı tarixi daxil edin.")
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Laihənin bitmə tarixini daxil edin.")
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "status")
    private boolean status;

    @Column(name = "active")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<FileEntity> fileEntities;

    @Transient
    @IsImage(message = "Əlavə etdiyiniz faylın formatı ancaq (JPG, JPEG, IMG, PNG) ola bilər.")
    private MultipartFile coverImage;

}
