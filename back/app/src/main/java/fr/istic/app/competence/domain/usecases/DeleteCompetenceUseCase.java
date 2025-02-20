package fr.istic.app.competence.domain.usecases;

import fr.istic.app.competence.persistence.jpa.CompetenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class DeleteCompetenceUseCase {

    private final CompetenceRepository competenceRepository;

    @Transactional
    public void delete(Long id) {
        competenceRepository.deleteById(id);
    }
}
