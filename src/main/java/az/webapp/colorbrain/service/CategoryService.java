package az.webapp.colorbrain.service;


import az.webapp.colorbrain.model.entity.CategoryEntity;
import az.webapp.colorbrain.repository.BlogRepository;
import az.webapp.colorbrain.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;

    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id) {
        blogRepository.deleteCategoryFromBlogs(id);
        categoryRepository.deleteById(id);
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
