package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.CategoryEntity;
import az.webapp.colorbrain.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public String getAllCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/allCategoryPage";
    }

    @GetMapping("/{id}")
    public String getCategoryById(
            @PathVariable("id") CategoryEntity categoryEntity,
            Model model
    ) {
        model.addAttribute("category", categoryEntity);
        return "admin/oneCategoryPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("categoryEntity", new CategoryEntity());
        return "admin/createCategoryPage";
    }

    @PostMapping("/create")
    public String saveCategory(CategoryEntity categoryEntity) {
        categoryService.saveCategory(categoryEntity);
        return "redirect:/category/all";
    }

    @PostMapping("/update")
    public String updateCategory(CategoryEntity categoryEntity) {
        categoryService.updateCategory(categoryEntity);
        return "redirect:/category/all";
    }

    @PostMapping("/delete")
    public String deleteCategory(
            @RequestParam("id") Long id
    ) {
        categoryService.deleteCategory(id);
        return "redirect:/category/all";
    }

}
