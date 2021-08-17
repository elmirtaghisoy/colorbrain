package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.component.criteria.MediaSearchCriteria;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.model.entity.MediaEntity;
import az.webapp.colorbrain.service.MediaService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

import static az.webapp.colorbrain.util.SearchUtil.mediaSearchPathBuilder;

@Controller
@RequestMapping(value = "/media")
@AllArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @GetMapping("/all")
    public String getAllMedia(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "8") int size,
            @RequestParam(value = "from", required = false, defaultValue = "2000-01-01") String fromDate,
            @RequestParam(value = "to", required = false, defaultValue = "2100-01-01") String toDate,
            @RequestParam(value = "header", required = false) String header,
            HttpServletRequest request,
            Model model
    ) {
        var criteria = new MediaSearchCriteria();
        criteria.setFromDate(fromDate);
        criteria.setToDate(toDate);
        criteria.setHeader(header);
        Paged<MediaEntity> list = mediaService.searchAllMedia(
                page,
                size,
                criteria
        );
        model.addAttribute("objectList", list);
        model.addAttribute("srcUrl", mediaSearchPathBuilder(request));
        return "admin/allMediaPage";
    }

    @GetMapping("/allf")
    public String getAllMediaF(Model model) {
        model.addAttribute("medias", mediaService.getAllMedia(0, 0));
        return "client/cb_media";
    }

    @GetMapping("/{id}")
    public String getOneMediaById(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("mediaEntity", mediaService.getOneMediaById(id));
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
            BindingResult bindingResult,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/createMediaPage";
        }
        mediaService.saveMedia(mediaEntity, files);
        return "redirect:/media/all";
    }

    @PostMapping("/update")
    public String updateMedia(
            @Valid @ModelAttribute("mediaEntity") MediaEntity mediaEntity,
            BindingResult bindingResult
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/oneMediaPage";
        }
        mediaService.updateMedia(mediaEntity);
        return "redirect:/media/all";
    }

    @PostMapping("/delete")
    public String deleteMedia(
            @RequestParam("id") Long id
    ) {
        mediaService.deleteMedia(id);
        return "redirect:/media/all";
    }

    @PostMapping("/{id}/files/save")
    public String saveFilesByMediaId(
            @PathVariable("id") Long id,
            @NotNull @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        mediaService.saveAdditionalMediaFiles(files, id);
        return "redirect:/media/" + id + "/files";
    }

    @PostMapping("{id}/files/delete")
    public String deleteFileByTrainingId(
            @RequestParam("fileId") Long fileId,
            @PathVariable("id") Long id
    ) {
        mediaService.deleteMediaFileByFileId(fileId);
        return "redirect:/media/" + id + "/files";
    }

}
