package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.MemberEntity;
import az.webapp.colorbrain.service.TeamMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(value = "/team-member")
@AllArgsConstructor
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

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
        model.addAttribute("teamMemberEntity", teamMemberService.getOneTeamMemberById(id));
        return "admin/oneTeamMemberPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("teamMemberEntity", new MemberEntity());
        return "admin/createTeamMemberPage";
    }

    @PostMapping("/create")
    public String saveTeamMember(
            @Valid @ModelAttribute("teamMemberEntity") MemberEntity memberEntity,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createTeamMemberPage";
        }
        teamMemberService.saveTeamMember(memberEntity);

        return "redirect:/team-member/all";
    }

    @PostMapping("/update")
    public String updateTeamMember(
            @Valid @ModelAttribute("teamMembersEntity") MemberEntity memberEntity,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/oneTeamMemberPage";
        }
        teamMemberService.updateTeamMember(memberEntity);
        return "redirect:/team-member/all";
    }

    @PostMapping("/delete")
    public String deleteTeamMembers(
            @RequestParam("id") Long id
    ) {
        teamMemberService.deleteTeamMember(id);
        return "redirect:/team-member/all";
    }

}
