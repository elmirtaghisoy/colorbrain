package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("rowCounter", 0L);
        return "admin/allTrainingPage";
    }

    @GetMapping("/{id}")
    public String getOneTrainingById(
            @PathVariable("id") TrainingEntity trainingEntity,
            Model model
    ) {
        model.addAttribute("training", trainingEntity);
        return "admin/oneTrainingPage";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "admin/createTrainingPage";
    }

    @PostMapping("/create")
    public String saveTraining(TrainingEntity trainingEntity) {
        trainingService.createTraining(trainingEntity);
        return "redirect:/training/active";
    }

    @PostMapping("/delete")
    public String deleteTraining(TrainingEntity trainingEntity) {
        trainingService.deleteTraining(trainingEntity);
        return "redirect:/training/active";
    }

    @PostMapping("/update")
    public String updateTraining(
            TrainingEntity trainingEntity
    ) {
        trainingService.updateTraining(trainingEntity);
        return "redirect:/training/active";
    }

}
