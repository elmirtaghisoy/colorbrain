package az.webapp.colorbrain.model.entity;

import az.webapp.colorbrain.component.annotation.IsImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "teamMembers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Üzvün adını daxil edin.")
    @Column(name = "member_name")
    private String memberName;

    @NotBlank(message = "Üzvün soyadını daxil edin.")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "Üzv haqqında məlumatı daxil edin.")
    @Column(name = "about")
    private String about;

    @Column(name = "member_image")
    private String memberImage;

    @NotBlank(message = "Üzvün vəzifəsini daxil edin.")
    @Column(name = "position")
    private String position;

    @Column(name = "active")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_members")
    private List<FileEntity> fileEntities;

    @Transient
    @IsImage(message = "Əlavə etdiyiniz faylın formatı ancaq (JPG, JPEG, IMG, PNG) ola bilər.")
    private MultipartFile coverImage;

}
