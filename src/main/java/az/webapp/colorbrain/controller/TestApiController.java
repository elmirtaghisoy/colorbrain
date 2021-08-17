package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.dto.request.TrainingRequest;
import az.webapp.colorbrain.model.dto.response.StandardResponse;
import az.webapp.colorbrain.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class TestApiController {

    @Autowired
    TrainingService service;

//    @GetMapping("/all")
//    public Paged<BlogEntity> getAllBlog(
//            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//            @RequestParam(value = "size", required = false, defaultValue = "8") int size,
//            @RequestParam(value = "from", required = false, defaultValue = "2000-01-01") String fromDate,
//            @RequestParam(value = "to", required = false, defaultValue = "2100-01-01") String toDate,
//            @RequestParam(value = "header", required = false) String header,
//            @RequestParam(value = "category", required = false) Long categoryId
//    ) {
//        var criteria = new BlogSearchCriteria();
//        criteria.setFromDate(fromDate);
//        criteria.setToDate(toDate);
//        criteria.setHeader(header);
//        criteria.setCategoryId(categoryId);
//        return service.searchAllBlog(
//                page,
//                size,
//                criteria
//        );
//    }

    @PostMapping("test")
    public StandardResponse get(
            @Valid @RequestBody TrainingRequest request
    ) throws IOException {
        return service.saveTraining(request, null);
    }
}
