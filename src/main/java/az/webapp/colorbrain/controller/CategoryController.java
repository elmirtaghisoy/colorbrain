package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/category")
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/")
    public void getAllCategoty() {

    }

}
