package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.TeamMemberEntity;
import az.webapp.colorbrain.service.TeamMemberService;
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
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/team-member")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("/all")
    public String getAllTeamMembers(Model model) {
        model.addAttribute("teamMembers", teamMemberService.getAllTeamMembers());
        return "admin/allTeamMemberPage";
    }

    @GetMapping("/allf")
    public String getAllTeamMembersF(Model model) {
        model.addAttribute("teamMembers", teamMemberService.getAllTeamMembers());
        return "client/cb_team";
    }

    @GetMapping("/{id}")
    public String getOneTeamMemberById(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("teamMemberEntity", teamMemberService.getOneTeamMembersById(id));
        return "admin/oneTeamMemberPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("teamMemberEntity", new TeamMemberEntity());
        return "admin/createTeamMemberPage";
    }

    @PostMapping("/create")
    public String saveTeamMember(
            @Valid @ModelAttribute("teamMemberEntity") TeamMemberEntity teamMemberEntity,
            BindingResult bindingResult,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createTeamMemberPage";
        }
        teamMemberService.saveTeamMembers(teamMemberEntity, files);

        return "redirect:/team-member/all";
    }

    @PostMapping("/update")
    public String updateTeamMembers(
            @Valid @ModelAttribute("teamMembersEntity") TeamMemberEntity teamMemberEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/oneTeamMemberPage";
        }
        teamMemberService.updateTeamMembers(teamMemberEntity);
        return "redirect:/team-member/all";
    }

    @PostMapping("/delete")
    public String deleteTeamMembers(TeamMemberEntity teamMemberEntity) {
        teamMemberService.deleteTeamMembers(teamMemberEntity);
        return "redirect:/team-member/all";
    }

}
