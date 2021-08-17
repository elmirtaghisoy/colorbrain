package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.component.criteria.NewsSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.service.NewsService;
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

import static az.webapp.colorbrain.util.SearchUtil.newsSearchPathBuilder;

@Controller
@RequestMapping(value = "/news")
@AllArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/all")
    public String getAllNews(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "8") int size,
            @RequestParam(value = "from", required = false, defaultValue = "2000-01-01") String fromDate,
            @RequestParam(value = "to", required = false, defaultValue = "2100-01-01") String toDate,
            @RequestParam(value = "header", required = false) String header,
            HttpServletRequest request,
            Model model
    ) {
        var criteria = new NewsSearchCriteria();
        criteria.setFromDate(fromDate);
        criteria.setToDate(toDate);
        criteria.setHeader(header);
        Paged<NewsEntity> list = newsService.searchAllNews(
                page,
                size,
                criteria
        );
        model.addAttribute("objectList", list);
        model.addAttribute("srcUrl", newsSearchPathBuilder(request));
        return "admin/allNewsPage";
    }

    @GetMapping("/allf")
    public String getAllNewsF(Model model) {
        model.addAttribute("newsList", newsService.getAllNews(0, 0));
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
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/oneNewsPage";
        }
        newsService.updateNews(newsEntity);
        return "redirect:/news/all";
    }

    @PostMapping("/delete")
    public String deleteNews(
            @RequestParam("id") Long id
    ) {
        newsService.deleteNews(id);
        return "redirect:/news/all";
    }

    @PostMapping("{id}/files/save")
    public String saveFilesByNewsId(
            @PathVariable("id") Long id,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        newsService.saveAdditionalNewsFiles(files, id);
        return "redirect:/news/" + id + "/files";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByNewsId(
            @RequestParam("fileId") Long fileId,
            @PathVariable("id") Long id
    ) {
        newsService.deleteNewsFilesByFileId(fileId);
        return "redirect:/news/" + id + "/files";
    }

}
