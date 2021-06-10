package az.webapp.colorbrain.model.entity;

import az.webapp.colorbrain.component.annotation.IsImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "media")
@Getter
@Setter
@ToString
public class MediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Paylaşımın adını daxil edin")
    @Column(name = "header")
    private String header;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active", columnDefinition = "int default 1")
    private boolean active;

    @Column(name = "cover_path")
    private String coverPath;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "media_id")
    private List<FileEntity> fileEntities;

    @Transient
    @IsImage(message = "Əlavə etdiyiniz faylın formatı ancaq (JPG, JPEG, IMG, PNG) ola bilər.")
    private MultipartFile coverImage;

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
