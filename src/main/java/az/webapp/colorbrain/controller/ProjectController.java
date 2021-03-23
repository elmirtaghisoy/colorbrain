package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public void getAllProject(){
        List<ProjectEntity> projectList = projectService.getAllProject();
        for (ProjectEntity pr:projectList) {
            System.out.println(pr);
        }
    }

    @GetMapping("/create")
    public void createProject(){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setHeader("first project header");
        projectEntity.setContext("first project contex");
        projectEntity.setCreatedAt(LocalDateTime.now());
        projectEntity.setImageCover("first project image cover");


        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileCategory(1);
        fileEntity.setFilePath("first project file path");
        fileEntity.setFileType(4);

        List<FileEntity> fileLists = new ArrayList<>();
        fileLists.add(fileEntity);

        projectEntity.setFileEntities(fileLists);


        projectService.createProject(projectEntity);

    }

}

