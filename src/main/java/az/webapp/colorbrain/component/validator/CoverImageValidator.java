package az.webapp.colorbrain.component.validator;

import az.webapp.colorbrain.component.annotation.IsImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoverImageValidator implements ConstraintValidator<IsImage, MultipartFile> {

        @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
            String[] extensions = new String[] {"jpg","jpeg","png","jfif","img"};
            for (String extension : extensions){
                if (file.getOriginalFilename().contains(extension)){
                    return true;
                }
            }
        return file.getOriginalFilename().contains("jpg");
    }
}
