package fr.istic.app.profil.domain.usecases;

import fr.istic.app.matchProfilCompetence.persistence.jpa.MatchProfilCompetenceRepository;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteProfilUseCase {

    private final ProfilRepository profilRepository;
    private final MatchProfilCompetenceRepository matchProfilCompetenceRepository;

    public void delete(long id) {
        this.matchProfilCompetenceRepository.deleteByProfilId(id);
        profilRepository.deleteById(id);
    }
}
