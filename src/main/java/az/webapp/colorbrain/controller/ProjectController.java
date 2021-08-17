package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.component.criteria.ProjectSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

import static az.webapp.colorbrain.util.SearchUtil.projectSearchPathBuilder;

@Controller
@RequestMapping(value = "/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/all")
    public String getAllProject(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "8") int size,
            @RequestParam(value = "from", required = false, defaultValue = "2000-01-01") String fromDate,
            @RequestParam(value = "to", required = false, defaultValue = "2100-01-01") String toDate,
            @RequestParam(value = "header", required = false) String header,
            @RequestParam(value = "reg", required = false) Integer reg,
            @RequestParam(value = "status", required = false) Integer status,
            HttpServletRequest request,
            Model model
    ) {
        var criteria = new ProjectSearchCriteria();
        criteria.setFromDate(fromDate);
        criteria.setToDate(toDate);
        criteria.setHeader(header);
        criteria.setStatus(status);
        criteria.setReg(reg);
        Paged<ProjectEntity> list = projectService.searchAllProject(
                page,
                size,
                criteria
        );
        model.addAttribute("objectList", list);
        model.addAttribute("srcUrl", projectSearchPathBuilder(request));
        return "admin/allProjectPage";
    }

    @GetMapping("/activef")
    public String getAllProjectF(Model model) {
        model.addAttribute("projects", projectService.getAllActiveProject(1, 8));
        return "client/cb_project";
    }

    @GetMapping("/{id}/files")
    public String getAllFilesByProjectId(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("files", projectService.getAllFilesByProjectId(id));
        model.addAttribute("isFinished", projectService.getProjectStatus(id));
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
        return "redirect:/project/all";
    }

    @PostMapping("{id}/files/save")
    public String saveFilesByProjectId(
            @PathVariable("id") Long id,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        projectService.saveAdditionalProjectFiles(files, id);
        return "redirect:/project/" + id + "/files";
    }

    @PostMapping("/delete")
    public String deleteProject(
            @RequestParam("id") Long id
    ) {
        projectService.deleteProject(id);
        return "redirect:/project/active";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByProjectId(
            @RequestParam("fileId") Long fileId,
            @PathVariable("id") Long id
    ) {
        projectService.deleteProjectFileByFileId(fileId);
        return "redirect:/project/" + id + "/files";
    }

    @PostMapping("/update")
    public String updateProject(
            @Valid @ModelAttribute("projectEntity") ProjectEntity projectEntity,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/oneProjectPage";
        }
        projectService.updateProject(projectEntity);
        return "redirect:/project/all";
    }
}

