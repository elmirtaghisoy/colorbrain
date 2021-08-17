package az.webapp.colorbrain.model.entity;

import az.webapp.colorbrain.component.annotation.IsImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import java.util.List;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

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
    @JoinColumn(name = "ref_object_id")
    private List<FileEntity> fileEntities;

    @Column(name = "folder_uuid")
    private String folderUuid;

    @Transient
    @IsImage(message = "Əlavə etdiyiniz faylın formatı ancaq (JPG, JPEG, IMG, PNG) ola bilər.")
    private MultipartFile cover;

    @PrePersist
    public void onCreate() {
        active = true;
    }

}
