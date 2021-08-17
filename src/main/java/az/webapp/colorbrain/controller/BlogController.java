package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.component.criteria.BlogSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.model.entity.BlogEntity;
import az.webapp.colorbrain.service.BlogService;
import az.webapp.colorbrain.service.CategoryService;
import lombok.AllArgsConstructor;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

import static az.webapp.colorbrain.util.SearchUtil.mediaSearchPathBuilder;

@Controller
@RequestMapping(value = "/blog")
@AllArgsConstructor
public class BlogController {

    private final BlogService blogService;

    private final CategoryService categoryService;

    @GetMapping("/all")
    public String getAllBlog(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "8") int size,
            @RequestParam(value = "from", required = false, defaultValue = "2000-01-01") String fromDate,
            @RequestParam(value = "to", required = false, defaultValue = "2100-01-01") String toDate,
            @RequestParam(value = "header", required = false) String header,
            @RequestParam(value = "category", required = false) Long categoryId,
            HttpServletRequest request,
            Model model
    ) {
        var criteria = new BlogSearchCriteria();
        criteria.setFromDate(fromDate);
        criteria.setToDate(toDate);
        criteria.setHeader(header);
        criteria.setCategoryId(categoryId);
        Paged<BlogEntity> list = blogService.searchAllBlog(
                page,
                size,
                criteria
        );
        model.addAttribute("objectList", list);
        model.addAttribute("categoryList", categoryService.getAllCategory());
        model.addAttribute("srcUrl", mediaSearchPathBuilder(request));
        return "admin/allBlogPage";
    }

    @GetMapping("/allf")
    public String getAllBlogF(Model model) {
        model.addAttribute("blogs", blogService.getAllBlog(1, 8));
        model.addAttribute("categories", categoryService.getAllCategory());
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
            @PathVariable("id") Long id,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        blogService.saveAdditionalBlogFiles(files, id);
        return "redirect:/blog/" + id + "/files";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByProjectId(
            @RequestParam("fileId") Long fileId,
            @PathVariable("id") Long id
    ) {
        blogService.deleteBlogFileById(fileId);
        return "redirect:/blog/" + id + "/files";
    }

    @PostMapping("/delete")
    public String deleteProject(
            @RequestParam("id") Long id
    ) {
        blogService.deleteBlog(id);
        return "redirect:/blog/all";
    }

    @PostMapping("/update")
    public String updateBlog(
            @Valid @ModelAttribute("blogEntity") BlogEntity blogEntity,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/oneBlogPage";
        }
        blogService.updateBlog(blogEntity);
        return "redirect:/blog/all";
    }
}
