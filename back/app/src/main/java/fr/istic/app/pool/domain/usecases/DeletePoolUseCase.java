package fr.istic.app.pool.domain.usecases;

import fr.istic.app.matchPoolCompetence.persistence.jpa.MatchPoolCompetenceRepository;
import fr.istic.app.pool.persistence.jpa.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePoolUseCase {

    private final PoolRepository poolRepository;
    private final MatchPoolCompetenceRepository matchPoolCompetenceRepository;

    public void delete(Long id) {

        this.matchPoolCompetenceRepository.deleteAllByProfilId(id);
        this.poolRepository.deleteById(id);
    }
}
