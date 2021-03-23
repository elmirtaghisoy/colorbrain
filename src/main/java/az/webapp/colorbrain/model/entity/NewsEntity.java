package az.webapp.colorbrain.model.entity;

import lombok.*;

import javax.persistence.*;
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

    @Column(name = "header")
    private String header;

    @Column(name = "context")
    private String context;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "image_cover")
    private String imageCover;

    @Column(name = "status")
    private boolean status;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "newsEntity", cascade = CascadeType.ALL)
    private List<FileEntity> fileEntities;

}
