package az.webapp.colorbrain.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blog")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Blog {


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

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "image_cover")
    private String imageCover;

    @Column(name = "active")
    private boolean active;


//    @OneToMany(mappedBy = "blog")
//    private List<File> files;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


}