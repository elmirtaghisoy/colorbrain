package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.NewsEntity;
import az.webapp.colorbrain.model.entity.TeamMembersEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.TeamMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TeamMembersService {

    @Autowired
    private TeamMembersRepository teamMembersRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<TeamMembersEntity> getAllTeamMembers() {
        return teamMembersRepository.findAll();
    }

    public void saveTeamMembers(TeamMembersEntity teamMembersEntity,MultipartFile file) throws IOException {
         teamMembersEntity.setMemberImage(FileService.saveSingle(file));
     teamMembersRepository.save(teamMembersEntity);
    }



    public void updateTeamMembers(TeamMembersEntity teamMembersEntity) {
        teamMembersRepository.save(teamMembersEntity);
    }

    public TeamMembersEntity getOneTeamMembersById(Long id) {
        return teamMembersRepository.getOne(id);
    }

    public void deleteTeamMembers(TeamMembersEntity teamMembersEntity) {
        teamMembersRepository.delete(teamMembersEntity);
    }

    public List<TeamMembersEntity> getAllFilesByTeamMembersId(Long id) {
        return fileRepository.findAllByTeamMembersEntity_IdOrderByFileTypeAsc(id);
    }
}
