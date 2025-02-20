package fr.istic.app.profil.domain.usecases;

import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.profil.domain.model.Profil;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReadProfilUseCase {

    private final ProfilRepository profilRepository;

    @Transactional
    public Profil read(Long id) {
        return this.profilRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
