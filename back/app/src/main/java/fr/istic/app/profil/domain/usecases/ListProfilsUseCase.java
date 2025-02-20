package fr.istic.app.profil.domain.usecases;

import fr.istic.app.profil.domain.model.Profil;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListProfilsUseCase {

    private final ProfilRepository profilRepository;

    @Transactional
    public List<Profil> list() {
        return this.profilRepository.findAll();
    }
}
