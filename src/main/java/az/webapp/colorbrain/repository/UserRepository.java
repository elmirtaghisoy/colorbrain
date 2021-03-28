package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.CUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CUser, Long> {
    CUser findByUsername(String username);
}
