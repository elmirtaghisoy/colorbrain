package az.webapp.colorbrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping("projectf")
    public String project() {
        return "client/cb_project";

    }

    @GetMapping("blogf")
    public String blog() {
        return "client/cb_blog";

    }

    @GetMapping("mediaf")
    public String media(){
        return "client/cb_media";
    }
}
