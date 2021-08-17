package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<MemberEntity, Long> {
    List<MemberEntity> findAll();

    @Transactional
    @Modifying
    @Query(
            "update MemberEntity tm set " +
                    "tm.about = :#{#teamMember.about}," +
                    "tm.memberImage = :#{#teamMember.memberImage}," +
                    "tm.memberName = :#{#teamMember.memberName}," +
                    "tm.surname = :#{#teamMember.surname}," +
                    "tm.position = :#{#teamMember.position} " +
                    "where tm.id = :#{#teamMember.id}"
    )
    void update(MemberEntity teamMember);

    @Query(
            nativeQuery = true,
            value = "select m.folder_uuid member m where m.id =:id limit 1"
    )
    String getFolderUUID(Long id);
}
