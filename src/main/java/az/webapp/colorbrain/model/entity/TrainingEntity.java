package az.webapp.colorbrain.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "training")
@Getter
@Setter
@ToString
public class TrainingEntity {

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

    @Column(name = "last_registration_day")
    private LocalDate lastRegistrationDay;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "trainer_name")
    private String trainerName;

    @Column(name = "training_price")
    private String trainingPrice;

    @Column(name = "registration_is_active")
    private boolean registrationIsActive;

    @Column(name = "status")
    private boolean status;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "trainingEntity", cascade = CascadeType.ALL)
    private List<FileEntity> fileEntities;

    @OneToMany(mappedBy = "trainingEntity", cascade = CascadeType.ALL)
    private List<ParticipantEntity> participantEntities;

}
