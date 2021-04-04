package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.BlogEntity;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.service.BlogService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/active")
    public String getAllBlog(Model model) {
        model.addAttribute("blogs", blogService.getAllActiveBlog());
        return "admin/allBlogPage";
    }

    @GetMapping("/finished")
    public String getAllFinishedBlog(Model model) {
        model.addAttribute("blogs", blogService.getAllFinishedBlog());
        return "admin/allBlogPage";
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
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("blogEntity", blogService.getOneBlogById(id));
        return "admin/oneBlogPage";
    }


    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("blogEntity", new BlogEntity());
        return "admin/createBlogPage";
    }

    @PostMapping("/create")
    public String saveBlog(
            @Valid @ModelAttribute("blogEntity") BlogEntity blogEntity,
            @NotNull @RequestParam("files") List<MultipartFile> files,
            @NotNull @RequestParam("coverImage") MultipartFile file,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createBlogPage";
        }
        blogService.saveBlog(blogEntity, files, file);

        return "redirect:/blog/active";
    }

    @PostMapping("{id}/files/save")
    public String saveFilesByBlogId(
            @PathVariable("id") BlogEntity blogEntity,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        blogService.saveAdditionalBlogFiles(files, blogEntity);
        return "redirect:/blog/" + blogEntity.getId() + "/files";
    }

    @PostMapping("/delete")
    public String deleteProject(BlogEntity blogEntity) {
        blogService.deleteBlog(blogEntity);
        return "redirect:/blog/active";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByProjectId(
            @RequestParam("fileId") FileEntity fileEntity,
            @PathVariable("id") BlogEntity blogEntity
    ) {
        blogService.deleteFileByBlogId(fileEntity);
        return "redirect:/blog/" + blogEntity.getId() + "/files";
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
        return "redirect:/blog/active";
    }
}
