package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @GetMapping("/create")
    public void createNews() {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setHeader("news header");
        newsEntity.setContext("news context");
        newsEntity.setCreatedAt(LocalDateTime.now());
        newsEntity.setImageCover("cover image");
        newsEntity.setActive(true);

        FileEntity fileEntity1 = new FileEntity();
        fileEntity1.setFileCategory(1);
        fileEntity1.setFilePath("file path");
        fileEntity1.setFileType(2);

        FileEntity fileEntity2 = new FileEntity();
        fileEntity2.setFileCategory(2);
        fileEntity2.setFilePath("file path");
        fileEntity2.setFileType(3);

        List<FileEntity> fileLists = new ArrayList<>();
        fileLists.add(fileEntity1);
        fileLists.add(fileEntity2);

        newsEntity.setFileEntities(fileLists);
        newsService.saveNews(newsEntity);
    }
}
