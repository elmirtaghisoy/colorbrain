package az.webapp.colorbrain.model.entity;

import az.webapp.colorbrain.component.annotation.DateRangeValidator;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableV2 implements Serializable {

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "start_registration_day")
    private LocalDate startRegistrationDay;

    @Column(name = "last_registration_day")
    private LocalDate lastRegistrationDay;

    @Column(name = "registration_is_active")
    private int registrationIsActive;

    @Column(name = "status", columnDefinition = "int default 1")
    private boolean status;

    @Column(name = "active", columnDefinition = "int default 1")
    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        active = true;
        status = true;
        createdAt = LocalDateTime.now();
    }
}
