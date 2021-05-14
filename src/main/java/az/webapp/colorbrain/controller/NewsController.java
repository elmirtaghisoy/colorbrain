package az.webapp.colorbrain.controller;


import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.service.NewsService;
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
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/all")
    public String getAllNews(Model model) {
        model.addAttribute("newsList", newsService.getAllNews());
        return "admin/allNewsPage";
    }

    @GetMapping("/allf")
    public String getAllNewsF(Model model) {
        model.addAttribute("newsList", newsService.getAllNews());
        return "client/cb_news";
    }

    @GetMapping("/{id}")
    public String getOneNewsById(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("newsEntity", newsService.getOneNewsById(id));
        return "admin/oneNewsPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("newsEntity", new NewsEntity());
        return "admin/createNewsPage";
    }

    @GetMapping("/{id}/files")
    public String getAllFilesByNewsId(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("files", newsService.getAllFilesByNewsId(id));
        return "admin/allFilesPage";
    }

    @PostMapping("/create")
    public String saveNews(
            @Valid @ModelAttribute("newsEntity") NewsEntity newsEntity,
            BindingResult bindingResult,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createNewsPage";
        }
        newsService.saveNews(newsEntity, files);

        return "redirect:/news/all";
    }

    @PostMapping("/update")
    public String updateNews(
            @Valid @ModelAttribute("newsEntity") NewsEntity newsEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/oneNewsPage";
        }
        newsService.updateNews(newsEntity);
        return "redirect:/news/all";
    }

    @PostMapping("/delete")
    public String deleteNews(NewsEntity newsEntity) {
        newsService.deleteNews(newsEntity);
        return "redirect:/news/all";
    }

    @PostMapping("{id}/files/save")
    public String saveFilesByNewsId(
            @PathVariable("id") NewsEntity newsEntity,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        newsService.saveAdditionalNewsFiles(files, newsEntity);
        return "redirect:/news/" + newsEntity.getId() + "/files";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByNewsId(
            @RequestParam("fileId") FileEntity fileEntity,
            @PathVariable("id") NewsEntity newsEntity
    ) {
        newsService.deleteFileByNewsId(fileEntity);
        return "redirect:/news/" + newsEntity.getId() + "/files";
    }

}
