package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.ProjectEntity;
import az.webapp.colorbrain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectEntity> getAllProject() {
        return projectRepository.findAll();
    }

    public void saveProject(ProjectEntity projectEntity) {
        projectRepository.save(projectEntity);
    }

}
