package az.webapp.colorbrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControllerUtil {

    @GetMapping("/previous-page")
    public String getPreviousPageFromFiles(HttpServletRequest request) {
        return "redirect:" + request.getHeader("Referer").replace("/files", "");
    }

}
