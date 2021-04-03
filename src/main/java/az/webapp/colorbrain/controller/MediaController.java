package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.MediaEntity;
import az.webapp.colorbrain.service.MediaService;
import lombok.extern.slf4j.Slf4j;
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
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("/all")
    public String getAllMedia(Model model) {
        model.addAttribute("medias", mediaService.getAllMedia());
        return "admin/allMediaPage";
    }

    @GetMapping("/{id}")
    public String getOneMediaById(
            @PathVariable("id") MediaEntity mediaEntity,
            Model model
    ) {
        model.addAttribute("mediaEntity", mediaService.getOneMediaById(mediaEntity.getId()));
        return "admin/oneMediaPage";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("mediaEntity", new MediaEntity());
        return "admin/createMediaPage";
    }

    @GetMapping("/{id}/files")
    public String getAllFilesByMediaId(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("files", mediaService.getAllFilesByMediaId(id));
        return "admin/allFilesPage";
    }

    @PostMapping("/create")
    public String saveMedia(
            @Valid @ModelAttribute("mediaEntity") MediaEntity mediaEntity,
            @NotNull @RequestParam("files") List<MultipartFile> files,
            @NotNull @RequestParam("coverImage") MultipartFile file,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createMediaPage";
        }
        mediaService.saveMedia(mediaEntity, file, files);
        return "redirect:/media/all";
    }

    @PostMapping("/update")
    public String updateMedia(
            @Valid @ModelAttribute("mediaEntity") MediaEntity mediaEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/oneMediaPage";
        }
        mediaService.updateMedia(mediaEntity);
        return "redirect:/media/all";
    }

    @PostMapping("/delete")
    public String deleteMedia(MediaEntity mediaEntity) {
        mediaService.deleteMedia(mediaEntity);
        return "redirect:/media/all";
    }

    @PostMapping("/{id}/files/save")
    public String saveFilesByMediaId(
            @PathVariable("id") MediaEntity mediaEntity,
            @NotNull @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        mediaService.saveAdditionalMediaFiles(files, mediaEntity);
        return "redirect:/media/" + mediaEntity.getId() + "/files";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByTrainingId(
            @RequestParam("fileId") FileEntity fileEntity,
            @PathVariable("id") MediaEntity mediaEntity
    ) {
        mediaService.deleteFileByTrainingId(fileEntity);
        return "redirect:/media/" + mediaEntity.getId() + "/files";
    }

}
