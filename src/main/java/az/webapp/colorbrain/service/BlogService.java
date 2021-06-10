package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.CustomFile;
import az.webapp.colorbrain.model.entity.BlogEntity;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.repository.BlogRepository;
import az.webapp.colorbrain.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static az.webapp.colorbrain.config.MvcConfig.uploadPath;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<BlogEntity> getAllActiveBlog() {
        return blogRepository.findAllByActiveTrue();
    }

    public void saveBlog(BlogEntity blogEntity, List<MultipartFile> files) throws IOException {
        String uuidFolderName = UUID.randomUUID().toString();
//        CustomFile file = new CustomFile("blog", uuidFolderName, blogEntity.getCoverImage());
//        blogEntity.setCoverPath(FileService.saveSingle(file));
//        blogEntity.setFileEntities(FileService.saveMultiple(files, "blog", uuidFolderName));
        blogEntity.setCreatedAt(LocalDateTime.now());
        blogEntity.setActive(true);
        blogRepository.save(blogEntity);
    }

    public void saveAdditionalBlogFiles(List<MultipartFile> files, BlogEntity blogEntity) throws IOException {
        if (files.get(0).getSize() != 0) {
//            FileEntity fileEntity = fileRepository.findFirstByBlogEntityId(blogEntity.getId());
//            List<FileEntity> savedFiles = FileService.saveMultiple(files, "blog", fileEntity.getFolderUuid());
//            for (FileEntity file : savedFiles) {
//                file.setBlogEntity(blogEntity);
//                fileRepository.save(file);
//            }
        }
    }

    public void updateBlog(BlogEntity blogEntity) {
        BlogEntity blogEntityFromDb = getOneBlogById(blogEntity.getId());
        blogEntity.setCreatedAt(blogEntityFromDb.getCreatedAt());
        blogEntity.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blogEntity);
    }

    public void deleteBlog(BlogEntity blogEntity) {
        blogRepository.delete(blogEntity);
    }

    public BlogEntity getOneBlogById(Long id) {
        return blogRepository.getOne(id);
    }

    public List<FileEntity> getAllFilesByBlogId(Long id) {
//        return fileRepository.findAllByBlogEntity_IdOrderByFileTypeAsc(id);
    return null;
    }

    public void deleteFileByBlogId(FileEntity fileEntity) {
        fileRepository.delete(fileEntity);
        FileService.deleteFile(uploadPath + "/" + fileEntity.getFilePath());
    }
}
