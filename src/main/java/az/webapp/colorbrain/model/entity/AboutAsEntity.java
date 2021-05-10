package az.webapp.colorbrain.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "aboutAs")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AboutAsEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Məlumatı daxil edin.")
    @Column(name = "aboutAs")
    private String aboutAs;

//    @Column(name = "image_cover")
//    private String imageCover;
}
