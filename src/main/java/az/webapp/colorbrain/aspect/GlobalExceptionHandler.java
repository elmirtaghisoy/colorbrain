package az.webapp.colorbrain.aspect;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler extends DispatcherServlet {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(WebRequest req, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("uploadingError", "YÜKLƏDİYİNİZ FAYLLARIN ÜMUMİ ÖLÇÜSÜ 30MB-DAN BÖYÜK OLMALIDIR");
        return "redirect:" + req.getHeader("referer").substring(21);
    }

}