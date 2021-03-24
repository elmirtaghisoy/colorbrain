package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.service.TrainingService;
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
@RequestMapping(value = "/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @GetMapping("/active")
    public String getAllActiveTraining(Model model) {
        model.addAttribute("trainings", trainingService.getAllActiveTraining());
        return "admin/allTrainingPage";
    }

    @GetMapping("/finished")
    public String getAllFinishedTraining(Model model) {
        model.addAttribute("trainings", trainingService.getAllFinishedTraining());
        return "admin/allTrainingPage";
    }

    @GetMapping("/{id}")
    public String getOneTrainingById(
            @PathVariable("id") TrainingEntity trainingEntity,
            Model model
    ) {
        model.addAttribute("trainingEntity", trainingService.getOneTrainingById(trainingEntity.getId()));
        return "admin/oneTrainingPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("trainingEntity", new TrainingEntity());
        return "admin/createTrainingPage";
    }

    @PostMapping("/create")
    public String saveTraining(
            @Valid @ModelAttribute("trainingEntity") TrainingEntity trainingEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/createTrainingPage";
        }
        System.out.println(trainingEntity.toString());
        trainingService.saveTraining(trainingEntity);
        return "redirect:/training/active";
    }

    @PostMapping("/delete")
    public String deleteTraining(TrainingEntity trainingEntity) {
        trainingService.deleteTraining(trainingEntity);
        return "redirect:/training/active";
    }

    @PostMapping("/update")
    public String updateTraining(
            @Valid @ModelAttribute("trainingEntity") TrainingEntity trainingEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/oneTrainingPage";
        }
        trainingService.updateTraining(trainingEntity);
        return "redirect:/training/active";
    }

}
