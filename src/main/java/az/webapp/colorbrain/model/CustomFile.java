package az.webapp.colorbrain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class CustomFile {

    public CustomFile(String category, String folder, MultipartFile file) {
        this.category = category;
        this.folder = folder;
        this.file = file;
    }

    public String generateFolder() {
        return this.folder.replace(" ", "").toLowerCase();
    }

    private String category;
    private String folder;
    private MultipartFile file;
}
