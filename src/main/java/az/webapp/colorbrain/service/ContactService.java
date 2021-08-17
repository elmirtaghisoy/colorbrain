package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.ContactEntity;
import az.webapp.colorbrain.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public List<ContactEntity> getAllContact() {
        return contactRepository.findAll();
    }

    public void saveContact(ContactEntity contactEntity) {
        contactRepository.save(contactEntity);
    }

    public void updateContact(ContactEntity contactEntity) {
        contactRepository.save(contactEntity);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

}
