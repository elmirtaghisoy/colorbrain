package az.webapp.colorbrain.service;


import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<NewsEntity> getAllBasicNews() {
        return newsRepository.findAllByStatusTrue();
    }

    public List<NewsEntity> getAllLatestNews() {
        return newsRepository.findAllByStatusFalse();
    }

    public void createNews(NewsEntity newsEntity) {
        newsRepository.save(newsEntity);
    }
}
