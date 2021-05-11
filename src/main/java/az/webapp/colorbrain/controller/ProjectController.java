package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

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

    @GetMapping("/activef")
    public String getAllProjectF(Model model) {
        model.addAttribute("projects", projectService.getAllActiveProject());
        return "client/cb_project";
    }

    @GetMapping("/finishedf")
    public String getAllFinishedProjectF(Model model) {
        model.addAttribute("projects", projectService.getAllFinishedProject());
        return "client/cb_project";
    }

    @GetMapping("/finished")
    public String getAllFinishedProject(Model model) {
        model.addAttribute("projects", projectService.getAllFinishedProject());
        return "admin/allProjectPage";
    }

    @GetMapping("/{id}/files")
    public String getAllFilesByProjectId(
            @PathVariable("id") ProjectEntity projectEntity,
            Model model
    ) {
        model.addAttribute("files", projectService.getAllFilesByProjectId(projectEntity.getId()));
        model.addAttribute("isFinished", projectEntity.isStatus());
        return "admin/allFilesPage";
    }

    @GetMapping("/{id}")
    public String getOneProjectById(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("projectEntity", projectService.getOneProjectById(id));
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
            BindingResult bindingResult,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createProjectPage";
        }
        projectService.saveProject(projectEntity, files);

        return "redirect:/project/active";
    }

    @PostMapping("{id}/files/save")
    public String saveFilesByProjectId(
            @PathVariable("id") ProjectEntity projectEntity,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        projectService.saveAdditionalProjectFiles(files, projectEntity);
        return "redirect:/project/" + projectEntity.getId() + "/files";
    }

    @PostMapping("/delete")
    public String deleteProject(ProjectEntity projectEntity) {
        projectService.deleteProject(projectEntity);
        return "redirect:/project/active";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByProjectId(
            @RequestParam("fileId") FileEntity fileEntity,
            @PathVariable("id") ProjectEntity projectEntity
    ) {
        projectService.deleteFileByProjectId(fileEntity);
        return "redirect:/project/" + projectEntity.getId() + "/files";
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

