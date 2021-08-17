package az.webapp.colorbrain.service;

import az.webapp.colorbrain.component.CustomFile;
import az.webapp.colorbrain.model.entity.MemberEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static az.webapp.colorbrain.config.ApplicationConstants.MEDIA;
import static az.webapp.colorbrain.config.ApplicationConstants.MEMBER;
import static az.webapp.colorbrain.config.ApplicationConstants.MEMBER_DEFAULT_IMG_PATH;
import static az.webapp.colorbrain.util.CommonUtils.generateRandomFolderName;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final FileRepository fileRepository;

    public List<MemberEntity> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public void saveTeamMember(MemberEntity memberEntity) throws IOException {
        String uuidFolderName = generateRandomFolderName();
        if (memberEntity.getCover().isEmpty()) {
            memberEntity.setMemberImage(MEMBER_DEFAULT_IMG_PATH);
        } else {
            CustomFile file = CustomFile.builder()
                    .category(MEMBER)
                    .folder(uuidFolderName)
                    .file(memberEntity.getCover())
                    .build();
            memberEntity.setMemberImage(FileService.saveSingle(file));
        }
        teamMemberRepository.save(memberEntity);
    }

    public void updateTeamMember(MemberEntity memberEntity) throws IOException {
        var teamMemberFromDb = getOneTeamMemberById(memberEntity.getId());
        if (memberEntity.getCover().isEmpty()) {
            memberEntity.setMemberImage(teamMemberFromDb.getMemberImage());
        } else {
            String folderUUID = teamMemberRepository.getFolderUUID(memberEntity.getId());

            CustomFile file = CustomFile.builder()
                    .category(MEDIA)
                    .folder(folderUUID)
                    .file(memberEntity.getCover())
                    .build();

            memberEntity.setMemberImage(FileService.saveSingle(file));
        }
        teamMemberRepository.update(memberEntity);
    }

    public MemberEntity getOneTeamMemberById(Long id) {
        return teamMemberRepository.getOne(id);
    }

    public void deleteTeamMember(Long id) {
        teamMemberRepository.deleteById(id);
    }

}
