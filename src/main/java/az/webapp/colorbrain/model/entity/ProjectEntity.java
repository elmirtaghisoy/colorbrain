package az.webapp.colorbrain.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Column(name = "image_cover")
    private String imageCover;

    @Column(name = "status")
    private boolean status;

    @Column(name = "active")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<FileEntity> fileEntities;

}
