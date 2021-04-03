package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Long> {
    List<TeamMemberEntity> findAll();
}
