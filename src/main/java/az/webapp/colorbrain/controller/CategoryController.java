package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.CategoryEntity;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/active")
    public String getAllCategory(Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        return "admin/allCategoryPage";
    }

    @GetMapping("/{id}")
    public String getOneCategoryById(@PathVariable("id")CategoryEntity categoryEntity, Model model){
        model.addAttribute("category",categoryEntity);
        return "admin/oneCategoryPage";
    }

    @PostMapping("/delete")
    public String deleteCategory(CategoryEntity categoryEntity) {
        categoryService.deleteCategory(categoryEntity);
        return "redirect:/category/active";
    }

    @PostMapping("/update")
    public String updateCategory( CategoryEntity categoryEntity){
        categoryService.updateCategory(categoryEntity);
        return "redirect:/category/active";
    }

    @PostMapping("/create")
    public String saveCategory( CategoryEntity categoryEntity){
        categoryService.saveCategory(categoryEntity);
        return "redirect:/category/active";
    }

    @GetMapping("/create")
    public String getCreatePage(){
        return "admin/createCategoryPage";
    }
}
