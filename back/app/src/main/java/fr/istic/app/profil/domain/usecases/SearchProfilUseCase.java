package fr.istic.app.profil.domain.usecases;

import fr.istic.app.common.domain.exception.SearchParamsAllNull;
import fr.istic.app.profil.domain.model.Profil;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class SearchProfilUseCase {

    private final ProfilRepository profilRepository;
    @Transactional
    public List<Profil> search(List<Long> competenceIds, Integer experience, LocalDate dateDebut, LocalDate dateFin) {

        boolean hasCompetences = competenceIds != null && !competenceIds.isEmpty();
        boolean hasExperience = experience != null;
        boolean hasDates = dateDebut != null && dateFin != null;

        if (hasCompetences && hasExperience && hasDates) {
            return profilRepository.search(competenceIds, experience, dateDebut, dateFin);
        }
        if (hasCompetences && hasExperience) {
            return profilRepository.search(competenceIds, experience);
        }
        if (hasCompetences && hasDates) {
            return profilRepository.search(competenceIds, dateDebut, dateFin);
        }
        if (hasExperience && hasDates) {
            return profilRepository.search(experience, dateDebut, dateFin);
        }
        if (hasCompetences) {
            return profilRepository.search(competenceIds);
        }
        if (hasExperience) {
            return profilRepository.search(experience);
        }
        if (hasDates) {
            return profilRepository.search(dateDebut, dateFin);
        }
            throw new SearchParamsAllNull();

    }
}
