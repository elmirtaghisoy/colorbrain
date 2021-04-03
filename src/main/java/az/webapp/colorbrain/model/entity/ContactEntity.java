package az.webapp.colorbrain.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "contact")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "")
    @Column(name = "contact_address")
    private String contactAddress;

    @NotBlank(message = "")
    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "icon")
    private String icon;

    @Column(name = "active")
    private boolean active;

}
