package az.webapp.colorbrain.component.validator;

import az.webapp.colorbrain.component.annotation.IsImage;
import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CoverImageValidator implements ConstraintValidator<IsImage, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        String[] extensions = new String[]{"jpg", "jpeg", "png", "img"};
        for (String extension : extensions) {
            if (Files.getFileExtension(Objects.requireNonNull(file.getOriginalFilename())).equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
