package az.webapp.colorbrain.service;

import az.webapp.colorbrain.component.CustomFile;
import az.webapp.colorbrain.component.criteria.BlogSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.component.paging.Paging;
import az.webapp.colorbrain.component.query.SearchQueries;
import az.webapp.colorbrain.model.entity.BlogEntity;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.repository.BlogRepository;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.view.DeleteFileView;
import az.webapp.colorbrain.repository.view.GetView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static az.webapp.colorbrain.config.ApplicationConstants.BLOG;
import static az.webapp.colorbrain.config.ApplicationConstants.BLOG_DEFAULT_IMG_PATH;
import static az.webapp.colorbrain.config.ApplicationConstants.MEDIA;
import static az.webapp.colorbrain.config.ApplicationConstants.PROJECT;
import static az.webapp.colorbrain.config.MvcConfig.uploadPath;
import static az.webapp.colorbrain.util.CommonUtils.generateRandomFolderName;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final FileRepository fileRepository;

    public Paged<BlogEntity> getAllBlog(int pageNumber, int size) {
        var request = PageRequest.of(pageNumber - 1, size);
        Page<BlogEntity> postPage = blogRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public void saveBlog(BlogEntity blogEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = generateRandomFolderName();
        blogEntity.setFolderUuid(uuidFolderName);

        if (blogEntity.getCover().isEmpty()) {
            blogEntity.setCoverPath(BLOG_DEFAULT_IMG_PATH);
        } else {
            CustomFile file = CustomFile.builder()
                    .category(PROJECT)
                    .folder(uuidFolderName)
                    .file(blogEntity.getCover())
                    .build();
            blogEntity.setCoverPath(FileService.saveSingle(file));
        }
        if (!files.get(0).isEmpty()) {
            blogEntity.setFileEntities(FileService.saveMultiple(BLOG, uuidFolderName, files));
        }
        blogRepository.save(blogEntity);
    }

    public void saveAdditionalBlogFiles(List<MultipartFile> files, Long id) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> fileList = FileService.saveMultiple(BLOG, blogRepository.getFolderUUID(id), files);
            fileList.forEach(file -> file.setRefObjectId(id));
            fileRepository.saveAll(fileList);
        }
    }

    public void updateBlog(BlogEntity blogEntity) throws IOException {
        var blogEntityFromDb = getOneBlogById(blogEntity.getId());
        blogEntity.setCreatedAt(blogEntityFromDb.getCreatedAt());
        blogEntity.setUpdatedAt(LocalDateTime.now());
        if (blogEntity.getCover().isEmpty()) {
            blogEntity.setCoverPath(blogEntityFromDb.getCoverPath());
        } else {
            String folderUUID = blogRepository.getFolderUUID(blogEntity.getId());
            CustomFile file = CustomFile.builder()
                    .category(MEDIA)
                    .folder(folderUUID)
                    .file(blogEntity.getCover())
                    .build();

            blogEntity.setCoverPath(FileService.saveSingle(file));
        }
        blogRepository.update(blogEntity);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public BlogEntity getOneBlogById(Long id) {
        return blogRepository.getOne(id);
    }

    public List<GetView.File> getAllFilesByBlogId(Long id) {
        return fileRepository.findAllByFileCategoryAndRefObjectId(BLOG, id);
    }

    public void deleteBlogFileById(Long fileId) {
        DeleteFileView file = fileRepository.getFileById(fileId);
        fileRepository.deleteByFileCategoryAndRefObjectId(BLOG, file.getId());
        FileService.deleteFile(uploadPath + "/" + file.getFilePath());
    }


    public Paged<BlogEntity> searchAllBlog(int page, int size, BlogSearchCriteria searchRequest) {
        Pageable pageRequest = PageRequest.of(page - 1, size);

        Page<BlogEntity> postPage = blogRepository.findAll(
                SearchQueries.createBlogSpecification(searchRequest),
                pageRequest
        );

        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), page, size));
    }
}
