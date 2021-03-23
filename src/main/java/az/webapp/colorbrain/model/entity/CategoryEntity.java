package az.webapp.colorbrain.model.entity;

import lombok.*;
<<<<<<< HEAD

=======
>>>>>>> 111a7b587a07d70dcc7c5d90353f32db25c2ff7d
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

<<<<<<< HEAD
//    @OneToMany
//    private List<Blog> blogs;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private List<NewsEntity> newsEntity;
=======
    @OneToMany
    private List<Blog> blogs;
>>>>>>> 111a7b587a07d70dcc7c5d90353f32db25c2ff7d
}
