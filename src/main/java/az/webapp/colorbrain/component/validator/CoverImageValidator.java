package az.webapp.colorbrain.component.validator;

import az.webapp.colorbrain.component.annotation.IsImage;
import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoverImageValidator implements ConstraintValidator<IsImage, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String[] extensions = new String[]{"jpg", "jpeg", "png", "img"};
            for (String extension : extensions) {
                if (file.isEmpty() || file == null || Files.getFileExtension(file.getOriginalFilename()).equals(extension)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return true;
        }
    }
}
