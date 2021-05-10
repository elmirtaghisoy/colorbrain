package az.webapp.colorbrain.service;

import az.webapp.colorbrain.model.entity.AboutAsEntity;
import az.webapp.colorbrain.repository.AboutAsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutAsService {

    @Autowired
    AboutAsRepository aboutAsRepository;

    public  AboutAsEntity getOneAboutAsById(Long id) {
       return aboutAsRepository.getOne(id); }

    public void updateAboutAs(AboutAsEntity aboutAsEntity) {
        aboutAsRepository.save(aboutAsEntity);
    }
}
