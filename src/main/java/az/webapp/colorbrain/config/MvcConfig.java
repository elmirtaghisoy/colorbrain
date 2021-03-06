package az.webapp.colorbrain.config;

import az.webapp.colorbrain.util.CommonUtils;
import com.sun.istack.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public static final String uploadPath = "/C:/TestProjects/colorbrain/uploads";

    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file://" + uploadPath + "/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
