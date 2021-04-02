package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.CUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping(value = {"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("cuser", new CUser());
        return "login";
    }

}
