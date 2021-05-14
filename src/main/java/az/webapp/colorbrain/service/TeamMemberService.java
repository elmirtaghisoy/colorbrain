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

    @Autowired
    private FileRepository fileRepository;

    public List<TeamMemberEntity> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public void saveTeamMembers(TeamMemberEntity teamMemberEntity, List<MultipartFile> files) throws IOException {
        teamMemberEntity.setMemberImage(FileService.saveSingle(teamMemberEntity.getCoverImage()));
        teamMemberEntity.setFileEntities(FileService.saveMultiple(files, "team"));
        teamMemberEntity.setActive(true);
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
