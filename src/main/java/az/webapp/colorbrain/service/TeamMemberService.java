package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.CustomFile;
import az.webapp.colorbrain.model.entity.TeamMemberEntity;
import az.webapp.colorbrain.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public List<TeamMemberEntity> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public void saveTeamMember(TeamMemberEntity teamMemberEntity) throws IOException {
//        CustomFile file = new CustomFile("teamMembers", "member", teamMemberEntity.getCoverImage());
//        teamMemberEntity.setMemberImage(FileService.saveSingle(file));
        teamMemberEntity.setActive(true);
        teamMemberRepository.save(teamMemberEntity);
    }

    public void updateTeamMember(TeamMemberEntity teamMemberEntity) {
        teamMemberRepository.save(teamMemberEntity);
    }

    public TeamMemberEntity getOneTeamMembersById(Long id) {
        return teamMemberRepository.getOne(id);
    }

    public void deleteTeamMember(TeamMemberEntity teamMemberEntity) {
        teamMemberRepository.delete(teamMemberEntity);
    }

}
