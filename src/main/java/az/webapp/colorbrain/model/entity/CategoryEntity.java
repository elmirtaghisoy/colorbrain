package az.webapp.colorbrain.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "active")
    private boolean active;

//    @OneToMany
//    private List<Blog> blogs;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private List<NewsEntity> newsEntity;
}
