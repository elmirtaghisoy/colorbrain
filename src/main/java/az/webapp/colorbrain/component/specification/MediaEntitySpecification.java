package az.webapp.colorbrain.component.specification;

import az.webapp.colorbrain.component.criteria.MediaSearchCriteria;
import az.webapp.colorbrain.model.entity.MediaEntity;
import az.webapp.colorbrain.model.entity.MediaEntity_;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MediaEntitySpecification implements Specification<MediaEntity> {

    private MediaSearchCriteria request;

    public MediaEntitySpecification(MediaSearchCriteria request) {
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<MediaEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(request)) {
            if (Objects.nonNull(request.getHeader())) {
                predicates.add(cb.like(root.get(MediaEntity_.header), "%" + request.getHeader() + "%"));
            }
            if (Objects.nonNull(request.getFromDate())) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get(MediaEntity_.createdAt), LocalDate.parse(request.getFromDate()).atStartOfDay())
                );
            }
            if (Objects.nonNull(request.getToDate())) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get(MediaEntity_.createdAt), LocalDate.parse(request.getToDate()).atStartOfDay())
                );
            }
/*
            if (Objects.nonNull(request.getStatus()) && request.getStatus() != -1) {
                predicates.add(cb.equal(root.get(ProjectEntity_.status), request.getStatus()));
            }
            if (Objects.nonNull(request.getTrainer())) {
                predicates.add(cb.like(root.get(TrainingEntity_.trainerName), "%" + request.getTrainer() + "%"));
            }
            if (Objects.nonNull(request.getReg()) && request.getReg() != -1) {
                predicates.add(cb.equal(root.get(TrainingEntity_.registrationIsActive), request.getReg()));
            }
 */
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
