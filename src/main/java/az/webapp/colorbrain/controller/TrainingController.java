package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/create")
    public void createTraining() {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setHeader("First Training Header");
        trainingEntity.setContext("First Training Context");
        trainingEntity.setCreatedAt(LocalDateTime.now());
        trainingEntity.setStartDate(LocalDate.of(2021, 3, 21));
        trainingEntity.setStartTime(LocalTime.of(10, 0));
        trainingEntity.setImageCover("First Training Cover Image");
        trainingEntity.setLastRegistrationDay(LocalDate.of(2021, 3, 15));
        trainingEntity.setTrainerName("First Trainer");
        trainingEntity.setStatus(false);
        trainingEntity.setTrainingPrice("5AZN");
        trainingEntity.setRegistrationIsActive(true);

        FileEntity fileEntity1 = new FileEntity();
        fileEntity1.setFileCategory(1);
        fileEntity1.setFilePath("first training file path1");
        fileEntity1.setFileType(4);

        FileEntity fileEntity2 = new FileEntity();
        fileEntity2.setFileCategory(1);
        fileEntity2.setFilePath("first training file path2");
        fileEntity2.setFileType(4);

        List<FileEntity> fileLists = new ArrayList<>();
        fileLists.add(fileEntity1);
        fileLists.add(fileEntity2);

        trainingEntity.setFileEntities(fileLists);

        trainingService.createTraining(trainingEntity);
    }

}
