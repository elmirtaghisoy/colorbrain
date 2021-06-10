package az.webapp.colorbrain.service;


import az.webapp.colorbrain.model.CustomFile;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static az.webapp.colorbrain.config.MvcConfig.uploadPath;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }

    public void saveNews(NewsEntity newsEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = UUID.randomUUID().toString();
//        CustomFile file = new CustomFile("news", uuidFolderName, newsEntity.getCoverImage());
//        newsEntity.setCoverPath(FileService.saveSingle(file));
//        newsEntity.setFileEntities(FileService.saveMultiple(files, "news", uuidFolderName));
        newsEntity.setCreatedAt(LocalDateTime.now());
        newsEntity.setActive(true);
        newsRepository.save(newsEntity);
    }

    public void updateNews(NewsEntity newsEntity) {
        NewsEntity newsEntityFromDb = getOneNewsById(newsEntity.getId());
        newsEntity.setCreatedAt(newsEntityFromDb.getCreatedAt());
        newsEntity.setUpdateAt(LocalDateTime.now());
        newsRepository.save(newsEntity);
    }

    public NewsEntity getOneNewsById(Long id) {
        return newsRepository.getOne(id);
    }

    public void deleteNews(NewsEntity newsEntity) {
        newsRepository.delete(newsEntity);
    }

    public void saveAdditionalNewsFiles(List<MultipartFile> files, NewsEntity newsEntity) throws IOException {
        if (files.get(0).getSize() != 0) {
//            FileEntity fileEntity = fileRepository.findFirstByNewsEntityId(newsEntity.getId());
//            List<FileEntity> savedFiles = FileService.saveMultiple(files, "news", fileEntity.getFolderUuid());
//            for (FileEntity file : savedFiles) {
//                file.setNewsEntity(newsEntity);
//                fileRepository.save(file);
//            }
        }
    }

    public void deleteFileByNewsId(FileEntity fileEntity) {
        fileRepository.delete(fileEntity);
        FileService.deleteFile(uploadPath + "/" + fileEntity.getFilePath());
    }

    public List<FileEntity> getAllFilesByNewsId(Long id) {
//        return fileRepository.findAllByNewsEntity_IdOrderByFileTypeAsc(id);
        return null;

    }

}

