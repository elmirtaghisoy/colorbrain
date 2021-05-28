package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.CustomFile;
import az.webapp.colorbrain.model.entity.FileEntity;
import com.google.common.io.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static az.webapp.colorbrain.config.MvcConfig.uploadPath;


@Service
public class FileService {

    public static String saveSingle(CustomFile customFile) throws IOException {
        String resultFilename = "";
        String currentFilePath = uploadPath + "/" + customFile.getCategory() + "/" + customFile.generateFolder();

        if (customFile.getFile() != null && !customFile.getFile().getOriginalFilename().isEmpty()) {
            File uploadDir = new File(currentFilePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuid = UUID.randomUUID().toString();
            resultFilename = uuid + "." + customFile.getFile().getOriginalFilename();
            customFile.getFile().transferTo(new File(currentFilePath + "/" + resultFilename));
        }
        return customFile.getCategory() + "/" + customFile.generateFolder() + "/" + resultFilename;
    }

    public static List<FileEntity> saveMultiple(List<MultipartFile> files, String category, String folder) throws IOException {
        List<FileEntity> savedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFolderUuid(folder);
            fileEntity.setFilePath(saveSingle(new CustomFile(category, folder, file)));
            fileEntity.setFileType(extensionDetector(file.getOriginalFilename()));
            fileEntity.setFileCategory(fileCategoryDetector(category));
            savedFiles.add(fileEntity);
        }
        return savedFiles;
    }

    public static int fileCategoryDetector(String fileCategory) {
        int fileCategoryInt = 0;
        switch (fileCategory) {
            case "training":
                fileCategoryInt = 1;
                break;
            case "news":
                fileCategoryInt = 2;
                break;
            case "media":
                fileCategoryInt = 3;
                break;
            case "blog":
                fileCategoryInt = 4;
                break;
            case "project":
                fileCategoryInt = 5;
                break;
        }
        return fileCategoryInt;
    }

    public static int extensionDetector(String fileName) {
        switch (Files.getFileExtension(fileName)) {
            case "jpg":
            case "png":
            case "jpeg":
            case "img":
            case "jfif":
                return 1;
            case "mp4":
            case "webm":
            case "flv":
            case "wmv":
                return 2;
            case "mp3":
            case "m4p":
            case "msv":
                return 3;
            case "pdf":
                return 4;
            default:
                return 5;
        }
    }
}
