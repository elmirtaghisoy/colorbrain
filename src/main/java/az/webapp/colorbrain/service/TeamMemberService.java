package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.TeamMemberEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public List<TeamMemberEntity> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public void saveTeamMembers(TeamMemberEntity teamMemberEntity, MultipartFile file) throws IOException {
        teamMemberEntity.setMemberImage(FileService.saveSingle(file));
        teamMemberRepository.save(teamMemberEntity);
    }

    public void updateTeamMembers(TeamMemberEntity teamMemberEntity) {
        teamMemberRepository.save(teamMemberEntity);
    }

    public TeamMemberEntity getOneTeamMembersById(Long id) {
        return teamMemberRepository.getOne(id);
    }

    public void deleteTeamMembers(TeamMemberEntity teamMemberEntity) {
        teamMemberRepository.delete(teamMemberEntity);
    }

}
