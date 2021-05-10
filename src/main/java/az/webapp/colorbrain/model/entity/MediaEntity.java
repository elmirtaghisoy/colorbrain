package az.webapp.colorbrain.model.entity;

import az.webapp.colorbrain.component.annotation.IsImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
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

    @Column(name = "active")
    private boolean active;

    @Column(name = "coverPath")
    private String coverPath;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id")
    private List<FileEntity> fileEntities;

    @Transient
    @IsImage(message = "Elave etdiyiniz faylin formati ancaq JPG ,JPEG,PNG ola biler")
    private MultipartFile coverImage;

}
