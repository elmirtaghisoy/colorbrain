package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.TeamMembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMembersRepository extends JpaRepository<TeamMembersEntity,Long> {
List<TeamMembersEntity> findAll();

}
