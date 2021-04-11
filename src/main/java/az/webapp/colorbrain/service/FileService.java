package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.FileEntity;
import com.google.common.io.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private static final String uploadPath = "/C:/javaProject/colorbrain/uploads";

    public static String saveSingle(MultipartFile file) throws IOException {
        String resultFilename = "";
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
        }
        return resultFilename;
    }

    public static List<FileEntity> saveMultiple(List<MultipartFile> files, String fileCategory) throws IOException {
        List<FileEntity> savedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFilePath(saveSingle(file));
            fileEntity.setFileType(extensionDetector(file.getOriginalFilename()));
            fileEntity.setFileCategory(fileCategoryDetector(fileCategory));
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
