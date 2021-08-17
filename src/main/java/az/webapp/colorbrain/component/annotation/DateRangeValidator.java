package az.webapp.colorbrain.component.annotation;

import az.webapp.colorbrain.model.entity.AuditableV2;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.ValidateDateRangeValidator.class)
public @interface DateRangeValidator {
    String message() default "INVALID DATE RANGE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ValidateDateRangeValidator implements ConstraintValidator<DateRangeValidator, AuditableV2> {
        @Override
        public boolean isValid(AuditableV2 auditableV2Object, ConstraintValidatorContext constraintValidatorContext) {
            if (Objects.nonNull(auditableV2Object.getLastRegistrationDay())
                    && Objects.nonNull(auditableV2Object.getStartDate())
                    && Objects.nonNull(auditableV2Object.getStartRegistrationDay())
            )
                return auditableV2Object.getStartRegistrationDay().isBefore(auditableV2Object.getLastRegistrationDay())
                        && auditableV2Object.getLastRegistrationDay().isBefore(auditableV2Object.getStartDate());
            return false;
        }
    }
}
