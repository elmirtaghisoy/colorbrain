package az.webapp.colorbrain.component.annotation;

import az.webapp.colorbrain.component.validator.CoverImageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoverImageValidator.class)
public @interface IsImage {
    String message() default "Invalid file type";

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
