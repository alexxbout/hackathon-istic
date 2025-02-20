package fr.istic.app.competence.domain.usecases;

import fr.istic.app.competence.domain.model.Competence;
import fr.istic.app.competence.persistence.jpa.CompetenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListCompetenceUseCase {

    private final CompetenceRepository competenceRepository;

    @Transactional
    public List<Competence> list() {
        return this.competenceRepository.findAll();
    }
}