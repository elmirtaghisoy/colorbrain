package az.webapp.colorbrain.component.query;

import az.webapp.colorbrain.component.criteria.BlogSearchCriteria;
import az.webapp.colorbrain.component.criteria.MediaSearchCriteria;
import az.webapp.colorbrain.component.criteria.NewsSearchCriteria;
import az.webapp.colorbrain.component.criteria.ProjectSearchCriteria;
import az.webapp.colorbrain.component.criteria.TrainingSearchCriteria;
import az.webapp.colorbrain.component.specification.BlogEntitySpecification;
import az.webapp.colorbrain.component.specification.MediaEntitySpecification;
import az.webapp.colorbrain.component.specification.NewsEntitySpecification;
import az.webapp.colorbrain.component.specification.ProjectSpecification;
import az.webapp.colorbrain.component.specification.TrainingSpecification;
import az.webapp.colorbrain.model.entity.BlogEntity;
import az.webapp.colorbrain.model.entity.MediaEntity;
import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public interface SearchQueries {

    static Specification<TrainingEntity> createTrainingSpecification(TrainingSearchCriteria request) {
        return new TrainingSpecification(request);
    }

    static Specification<ProjectEntity> createProjectSpecification(ProjectSearchCriteria request) {
        return new ProjectSpecification(request);
    }

    static Specification<NewsEntity> createNewsSpecification(NewsSearchCriteria request) {
        return new NewsEntitySpecification(request);
    }

    static Specification<MediaEntity> createMediaSpecification(MediaSearchCriteria request) {
        return new MediaEntitySpecification(request);
    }

    static Specification<BlogEntity> createBlogSpecification(BlogSearchCriteria request) {
        return new BlogEntitySpecification(request);
    }
}
