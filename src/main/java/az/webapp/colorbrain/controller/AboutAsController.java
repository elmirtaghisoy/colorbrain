package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.AboutAsEntity;
import az.webapp.colorbrain.service.AboutAsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/aboutAs")
public class AboutAsController {

    @Autowired
    AboutAsService aboutAsService;

    @GetMapping("/{id}")
    public String getOneAboutAsById(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("aboutAsEntity", aboutAsService.getOneAboutAsById(id));
        return "admin/aboutAsForm";
    }
    @PostMapping("/update")
    public String updateNews(
            @Valid @ModelAttribute("aboutAsEntity") AboutAsEntity aboutAsEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/aboutAsForm";
        }
        aboutAsService.updateAboutAs(aboutAsEntity);
        return "redirect:/aboutAsForm";
    }

}
