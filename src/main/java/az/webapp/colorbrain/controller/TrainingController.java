package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.component.criteria.TrainingSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.model.dto.request.TrainingRequest;
import az.webapp.colorbrain.model.dto.response.StandardResponse;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

import static az.webapp.colorbrain.util.SearchUtil.trainingSearchPathBuilder;

@Controller
@RequestMapping(value = "/training")
@AllArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/all")
    public String getAllTraining(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "8") int size,
            @RequestParam(value = "from", required = false, defaultValue = "2000-01-01") String fromDate,
            @RequestParam(value = "to", required = false, defaultValue = "2100-01-01") String toDate,
            @RequestParam(value = "header", required = false) String header,
            @RequestParam(value = "trainer", required = false) String trainer,
            @RequestParam(value = "reg", required = false) Integer reg,
            @RequestParam(value = "status", required = false) Integer status,
            HttpServletRequest request,
            Model model
    ) {
        var criteria = new TrainingSearchCriteria();
        criteria.setFromDate(fromDate);
        criteria.setToDate(toDate);
        criteria.setHeader(header);
        criteria.setTrainer(trainer);
        criteria.setReg(reg);
        criteria.setStatus(status);
        Paged<TrainingEntity> list = trainingService.searchAllTraining(
                page,
                size,
                criteria
        );
        model.addAttribute("objectList", list);
        model.addAttribute("srcUrl", trainingSearchPathBuilder(request));
        return "admin/allTrainingPage";
    }

    @GetMapping("/{id}/files")
    public String getAllFilesByTrainingId(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("files", trainingService.getAllFilesByTrainingId(id));
        model.addAttribute("isFinished", trainingService.getTrainingStatus(id));
        return "admin/allFilesPage";
    }

    @GetMapping("/{id}")
    public String getOneTrainingById(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("trainingEntity", trainingService.getOneTrainingById(id));
        return "admin/oneTrainingPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("training", new TrainingRequest());
        return "admin/createTrainingPage";
    }

    @PostMapping("/create")
    public String saveTraining(
            @Valid @ModelAttribute("training") TrainingRequest trainingRequest,
            BindingResult bindingResult,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createTrainingPage";
        }
        StandardResponse response = trainingService.saveTraining(trainingRequest, files);
        if (response.hasErrors()) {
            for (ObjectError error : (List<ObjectError>) response.getData().get("response")) {
                bindingResult.addError(error);
            }
            return "admin/createTrainingPage";
        }
        return "redirect:/training/all";
    }

    @PostMapping("{id}/files/save")
    public String saveFilesByTrainingId(
            @PathVariable("id") Long id,
            @NotBlank @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        trainingService.saveAdditionalTrainingFiles(files, id);
        return "redirect:/training/" + id + "/files";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByTrainingId(
            @RequestParam("fileId") Long fileId,
            @PathVariable("id") Long id
    ) {
        trainingService.deleteFileByTrainingId(fileId);
        return "redirect:/training/" + id + "/files";
    }

    @PostMapping("/delete")
    public String deleteTraining(
            @RequestParam("trainingId") Long id
    ) {
        trainingService.deleteTraining(id);
        return "redirect:/training/active";
    }

    @PostMapping("/update")
    public String updateTraining(
            @Valid @ModelAttribute("trainingEntity") TrainingEntity trainingEntity,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/oneTrainingPage";
        }
        trainingService.updateTraining(trainingEntity);
        return "redirect:/training/all";
    }

}
