package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.dto.request.TrainingRequest;
import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ServiceUtil {

    private ServiceUtil() {
    }

    public static List<ObjectError> validateRegistrationParams(TrainingRequest request) {
        List<ObjectError> errors = new ArrayList<>();
        if (request.getRegistrationIsActive() == 1) {

            if (request.getLastRegistrationDay().isBefore(request.getStartRegistrationDay())) {
                errors.add(new ObjectError("global", "Başlanğıc qeydiyyat tarixi son qeydiyyat tarixindən evvel olmalıdır."));
            }

            if (request.getStartDate().isBefore(request.getLastRegistrationDay())) {
                errors.add(new ObjectError("global", "Son qeydiyyat tarixi başlanğıc tarixinden əvvəl olmalıdır."));
            }

            if (request.getStartRegistrationDay().isEqual(LocalDate.now())) {
                request.setRegistrationIsActive("2");
            } else {
                request.setRegistrationIsActive("1");
            }

        } else {
            request.setRegistrationIsActive("0");
        }
        return errors;
    }
}
