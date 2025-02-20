package fr.istic.app.pool.domain.usecases;

import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.competence.persistence.jpa.CompetenceRepository;
import fr.istic.app.matchPoolCompetence.domain.model.MatchPoolCompetence;
import fr.istic.app.matchPoolCompetence.persistence.jpa.MatchPoolCompetenceRepository;
import fr.istic.app.pool.domain.model.Pool;
import fr.istic.app.pool.persistence.jpa.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreatePoolUseCase {

    private final PoolRepository poolRepository;
    private final CompetenceRepository competenceRepository;
    private final MatchPoolCompetenceRepository matchPoolCompetenceRepository;

    @Transactional
    public Pool create(CreatePoolUseCaseDto dto) {

        var competences = dto.competenceIds
                .stream()
                .map(competenceId -> competenceRepository.findById(competenceId).orElseThrow(() -> new NotFoundException("Competence", competenceId)))
                .toList();

        Pool pool = Pool.builder()
                .nom(dto.nom)
                .experience(dto.experience)
                .build();

        var savedPool = poolRepository.save(pool);
        var poolCompetenceRelations = competences
                .stream()
                .map(competence ->
                        MatchPoolCompetence
                                .builder()
                                .pool(savedPool)
                                .competence(competence)
                                .build()
                )
                .toList();

        this.matchPoolCompetenceRepository.saveAll(poolCompetenceRelations);

        return savedPool;
    }

    public record CreatePoolUseCaseDto(
            String nom,
            Integer experience,
            List<Long> competenceIds
    ) {
    }
}
