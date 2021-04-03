package az.webapp.colorbrain.repository;

import az.webapp.colorbrain.model.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactEntity,Long> {
}
