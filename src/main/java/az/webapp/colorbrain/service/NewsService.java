package az.webapp.colorbrain.service;

import az.webapp.colorbrain.component.CustomFile;
import az.webapp.colorbrain.component.criteria.NewsSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.component.paging.Paging;
import az.webapp.colorbrain.component.query.SearchQueries;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.NewsRepository;
import az.webapp.colorbrain.repository.view.DeleteFileView;
import az.webapp.colorbrain.repository.view.GetView;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static az.webapp.colorbrain.config.ApplicationConstants.MEDIA;
import static az.webapp.colorbrain.config.ApplicationConstants.NEWS;
import static az.webapp.colorbrain.config.ApplicationConstants.NEWS_DEFAULT_IMG_PATH;
import static az.webapp.colorbrain.config.MvcConfig.uploadPath;
import static az.webapp.colorbrain.util.CommonUtils.generateRandomFolderName;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final FileRepository fileRepository;

    public Paged<NewsEntity> getAllNews(int page, int size) {
        var request = PageRequest.of(page - 1, size);
        Page<NewsEntity> postPage = newsRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), page, size));
    }

    public void saveNews(NewsEntity newsEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = generateRandomFolderName();
        newsEntity.setFolderUuid(uuidFolderName);
        if (newsEntity.getCover().isEmpty()) {
            newsEntity.setCoverPath(NEWS_DEFAULT_IMG_PATH);
        } else {
            CustomFile file = CustomFile.builder()
                    .category(NEWS)
                    .folder(uuidFolderName)
                    .file(newsEntity.getCover())
                    .build();
            newsEntity.setCoverPath(FileService.saveSingle(file));
        }
        if (!files.get(0).isEmpty()) {
            newsEntity.setFileEntities(FileService.saveMultiple(NEWS, uuidFolderName, files));
        }
        newsRepository.save(newsEntity);
    }


    public void updateNews(NewsEntity newsEntity) throws IOException {
        var newsEntityFromDb = getOneNewsById(newsEntity.getId());
        newsEntity.setCreatedAt(newsEntityFromDb.getCreatedAt());
        newsEntity.setUpdatedAt(LocalDateTime.now());
        if (newsEntity.getCover().isEmpty()) {
            newsEntity.setCoverPath(newsEntityFromDb.getCoverPath());
        } else {
            String folderUUID = newsRepository.getFolderUUID(newsEntity.getId());
            CustomFile file = CustomFile.builder()
                    .category(MEDIA)
                    .folder(folderUUID)
                    .file(newsEntity.getCover())
                    .build();
            newsEntity.setCoverPath(FileService.saveSingle(file));
        }
        newsRepository.update(newsEntity);
    }


    public NewsEntity getOneNewsById(Long id) {
        return newsRepository.getOne(id);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    public void saveAdditionalNewsFiles(List<MultipartFile> files, Long id) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> fileList = FileService.saveMultiple(NEWS, newsRepository.getFolderUUID(id), files);
            fileList.forEach(file -> file.setRefObjectId(id));
            fileRepository.saveAll(fileList);
        }
    }

    public void deleteNewsFilesByFileId(Long fileId) {
        DeleteFileView file = fileRepository.getFileById(fileId);
        fileRepository.deleteByFileCategoryAndRefObjectId(NEWS, file.getId());
        FileService.deleteFile(uploadPath + "/" + file.getFilePath());
    }

    public List<GetView.File> getAllFilesByNewsId(Long id) {
        return fileRepository.findAllByFileCategoryAndRefObjectId(NEWS, id);
    }

    public Paged<NewsEntity> searchAllNews(int page, int size, NewsSearchCriteria searchRequest) {
        Pageable pageRequest = PageRequest.of(page - 1, size);

        Page<NewsEntity> postPage = newsRepository.findAll(
                SearchQueries.createNewsSpecification(searchRequest),
                pageRequest
        );

        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), page, size));
    }
}

