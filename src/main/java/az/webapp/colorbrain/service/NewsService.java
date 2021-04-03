package az.webapp.colorbrain.service;


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

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }

    public void saveNews(NewsEntity newsEntity, MultipartFile file, List<MultipartFile> files) throws IOException {
        newsEntity.setFileEntities(FileService.saveMultiple(files, "news"));
        newsEntity.setImageCover(FileService.saveSingle(file));
        newsEntity.setCreatedAt(LocalDateTime.now());
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
            List<FileEntity> savedFiles = FileService.saveMultiple(files, "news");
            for (FileEntity file : savedFiles) {
                file.setNewsEntity(newsEntity);
                fileRepository.save(file);
            }
        }
    }

    public void deleteFileByNewsId(FileEntity fileEntity) {
        fileRepository.delete(fileEntity);
    }

    public List<FileEntity> getAllFilesByNewsId(Long id) {
        return fileRepository.findAllByNewsEntity_IdOrderByFileTypeAsc(id);
    }

}

