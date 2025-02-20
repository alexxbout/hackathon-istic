package fr.istic.app.competence.domain.usecases;

import fr.istic.app.competence.domain.model.Competence;
import fr.istic.app.competence.persistence.jpa.CompetenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class CreateCompetenceUseCase {

    private final CompetenceRepository competenceRepository;

    @Transactional
    public Competence create(CreateCompetenceDto competence) {
        Competence competence1 = Competence.builder()
                .nom(competence.nom)
                .build();
        return this.competenceRepository.save(competence1);
    }

    public record CreateCompetenceDto(
            String nom
    ) {
    }
}
