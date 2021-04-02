package az.webapp.colorbrain.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "c_user")
public class CUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Emaili daxil edin")
    private String email;

    @NotNull(message = "Shifreni daxil edin")
    private String password;

    private boolean active;
}
