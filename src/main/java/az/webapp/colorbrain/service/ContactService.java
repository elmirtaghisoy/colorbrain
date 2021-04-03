package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.ContactEntity;
import az.webapp.colorbrain.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<ContactEntity> getAllContact() {
        return contactRepository.findAll();
    }

    public void deleteContact(ContactEntity contactEntity) {
        contactRepository.delete(contactEntity);
    }

    public void saveContact(ContactEntity contactEntity) {
        contactRepository.save(contactEntity);
    }

    public void updateContact(ContactEntity contactEntity) {
        contactRepository.save(contactEntity);
    }

//        public void createContact(ContactEntity contactEntity){
////        contactRepository.save(contactEntity);
//
//    }
}
