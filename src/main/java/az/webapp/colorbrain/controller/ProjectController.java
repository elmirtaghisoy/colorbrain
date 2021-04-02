package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/active")
    public String getAllProject(Model model) {
        model.addAttribute("projects", projectService.getAllActiveProject());
        return "admin/allProjectPage";
    }

    @GetMapping("/finished")
    public String getAllFinishedTraining(Model model) {
        model.addAttribute("projects", projectService.getAllFinishedProject());
        return "admin/allProjectPage";
    }

    @GetMapping("/{id}")
    public String getOneProjectById(
            @PathVariable("id") ProjectEntity projectEntity,
            Model model
    ) {
        model.addAttribute("projectEntity", projectService.getOneProjectById(projectEntity.getId()));
        return "admin/oneProjectPage";
    }


    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("projectEntity", new ProjectEntity());
        return "admin/createProjectPage";
    }

    @PostMapping("/create")
    public String saveProject(
            @Valid @ModelAttribute("projectEntity") ProjectEntity projectEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/createProjectPage";
        }
        projectService.saveProject(projectEntity);

        return "redirect:/project/active";
    }

    @PostMapping("/delete")
    public String deleteProject(ProjectEntity projectEntity) {
        projectService.deleteProject(projectEntity);
        return "redirect:/project/active";
    }


    @PostMapping("/update")
    public String updateProject(
            @Valid @ModelAttribute("projectEntity") ProjectEntity projectEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/oneProjectPage";
        }
        projectService.updateProject(projectEntity);
        return "redirect:/project/active";
    }
}

