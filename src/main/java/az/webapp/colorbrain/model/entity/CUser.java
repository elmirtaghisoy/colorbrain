package az.webapp.colorbrain.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "c_user")
public class CUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private boolean active;
}
