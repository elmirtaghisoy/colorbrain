package az.webapp.colorbrain.model.dto.request;

import az.webapp.colorbrain.component.annotation.IsImage;
import az.webapp.colorbrain.model.entity.FileEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
//@DateRangeValidator(message = "Başlanğıc qeydiyyat tarixi son qeydiyyat tarixindən əvvəl olmalıdır. Son qeydiyyat tarixi isə başlama tarixindən əvvəl olmalıdır!!!")
public class TrainingRequest {

    private Long id;

    @NotBlank(message = "Təlimin adını daxil edin.")
    private String header;

    @NotBlank(message = "Təlim haqqında məlumat daxil edin.")
    private String context;

    private String coverPath;

    @NotNull(message = "Təlimin başlayacağı saatı daxil edin.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotBlank(message = "Təlimçi (təlimçilərin) adını daxil edin.")
    private String trainerName;

    @Pattern(regexp = "[0-9]+", message = "Məbləği ancaq rəqəmlə ifadə edin.")
    private String trainingPrice;

    private List<FileEntity> fileEntities;

    private String folderUuid;

    @Transient
    @IsImage(message = "Əlavə etdiyiniz faylın formatı ancaq (JPG,JPEG,IMG,PNG) ola bilər.")
    private MultipartFile cover;

    @NotNull(message = "Başlanğıc qeydiyyat tarixini daxil edin.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startRegistrationDay;

    @NotNull(message = "Başlayacağı tarixi daxil edin.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Son qeydiyyat tarixini daxil edin.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastRegistrationDay;

    private int registrationIsActive;

    private boolean status;

    private boolean active;

    public void setRegistrationIsActive(String value) {
        if (value == null) {
            registrationIsActive = 0;
        } else if (value.equals("on")) {
            registrationIsActive = 1;
        }
    }
}
