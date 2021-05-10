package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.model.entity.TeamMembersEntity;
import az.webapp.colorbrain.service.TeamMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/teamMembers")
public class TeamMembersController {

    @Autowired
    private TeamMembersService teamMembersService;

    @GetMapping("/allTeamMembers")
    public String getAllTeamMembers(Model model) {
        model.addAttribute("teamMembersList", teamMembersService.getAllTeamMembers());
        return "admin/allTeamMembersPage";
    }

    @GetMapping("/{id}/files")
    public String getAllFilesByTeamMembersId(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("files", teamMembersService.getAllFilesByTeamMembersId(id));
        return "admin/allFilesPage";
    }

    @GetMapping("/{id}")
    public String getOneTeamMembersById(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("teamMembersEntity", teamMembersService.getOneTeamMembersById(id));
        return "admin/oneTeamMembersPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("teamMembersEntity", new TeamMembersEntity());
        return "admin/createTeamMembersPage";
    }

    @PostMapping("/create")
    public String saveTeamMembers(
            @Valid @ModelAttribute("teamMembersEntity") TeamMembersEntity teamMembersEntity,
            BindingResult bindingResult,
            @NotNull @RequestParam("memberImage") MultipartFile file

    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createTeamMembersPage";
        }
       teamMembersService.saveTeamMembers(teamMembersEntity,file);
        return "redirect:/teamMembers/allTeamMembers";
    }

    @PostMapping("/delete")
    public String deleteTeamMembers(TeamMembersEntity teamMembersEntity) {
        teamMembersService.deleteTeamMembers(teamMembersEntity);
        return "redirect:/teamMembers/allTeamMembers";
    }

    @PostMapping("/update")
    public String updateTeamMembers(
            @Valid @ModelAttribute("teamMembersEntity") TeamMembersEntity teamMembersEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/oneTeamMembersPage";
        }
        teamMembersService.updateTeamMembers(teamMembersEntity);
        return "redirect:/teamMembers/allTeamMembers";
    }

}
