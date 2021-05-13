package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.BlogEntity;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.service.BlogService;
import az.webapp.colorbrain.service.CategoryService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public String getAllBlog(Model model) {
        model.addAttribute("blogs", blogService.getAllActiveBlog());
        return "admin/allBlogPage";
    }

    @GetMapping("/allf")
    public String getAllBlogF(Model model) {
        model.addAttribute("blogs", blogService.getAllActiveBlog());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "client/cb_blog";
    }

    @GetMapping("/{id}/files")
    public String getAllFilesByBlogId(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("files", blogService.getAllFilesByBlogId(id));
        return "admin/allFilesPage";
    }

    @GetMapping("/{id}")
    public String getOneBlogById(
            @PathVariable("id") BlogEntity blogEntity,
            Model model
    ) {
        model.addAttribute("blogEntity", blogService.getOneBlogById(blogEntity.getId()));
        model.addAttribute("categories", categoryService.getAllCategoryWithoutCurrent(blogEntity.getCategoryEntity().getId()));
        return "admin/oneBlogPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("blogEntity", new BlogEntity());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/createBlogPage";
    }

    @PostMapping("/create")
    public String saveBlog(
            @Valid @ModelAttribute("blogEntity") BlogEntity blogEntity,
            BindingResult bindingResult,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createBlogPage";
        }
        blogService.saveBlog(blogEntity, files);
        return "redirect:/blog/all";
    }

    @PostMapping("{id}/files/save")
    public String saveFilesByBlogId(
            @PathVariable("id") BlogEntity blogEntity,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        blogService.saveAdditionalBlogFiles(files, blogEntity);
        return "redirect:/blog/" + blogEntity.getId() + "/files";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByProjectId(
            @RequestParam("fileId") FileEntity fileEntity,
            @PathVariable("id") BlogEntity blogEntity
    ) {
        blogService.deleteFileByBlogId(fileEntity);
        return "redirect:/blog/" + blogEntity.getId() + "/files";
    }

    @PostMapping("/delete")
    public String deleteProject(BlogEntity blogEntity) {
        blogService.deleteBlog(blogEntity);
        return "redirect:/blog/all";
    }

    @PostMapping("/update")
    public String updateBlog(
            @Valid @ModelAttribute("blogEntity") BlogEntity blogEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/oneBlogPage";
        }
        blogService.updateBlog(blogEntity);
        return "redirect:/blog/all";
    }
}
