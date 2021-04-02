package az.webapp.colorbrain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "news")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Xəbərin adını daxil edin.")
    @Column(name = "header")
    private String header;

    @NotBlank(message = "Xəbərin haqqında məlumat daxil edin.")
    @Column(name = "context")
    private String context;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "image_cover")
    private String imageCover;

    @Column(name = "active")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "news_id")
    private List<FileEntity> fileEntities;

}
