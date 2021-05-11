package az.webapp.colorbrain.service;


import az.webapp.colorbrain.model.entity.CategoryEntity;
import az.webapp.colorbrain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(CategoryEntity categoryEntity) {
        categoryRepository.delete(categoryEntity);
    }

    public void saveCategory(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
    }

    public void updateCategory(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
    }

    public List<CategoryEntity> getAllCategoryWithoutCurrent(Long id) {
        return categoryRepository.findAllByIdNot(id);
    }
}
