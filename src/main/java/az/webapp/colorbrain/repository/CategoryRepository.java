package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.CategoryEntity;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

}
