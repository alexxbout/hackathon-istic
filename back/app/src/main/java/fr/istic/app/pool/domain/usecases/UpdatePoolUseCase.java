package fr.istic.app.pool.domain.usecases;

import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.competence.persistence.jpa.CompetenceRepository;
import fr.istic.app.matchPoolCompetence.domain.model.MatchPoolCompetence;
import fr.istic.app.pool.domain.model.Pool;
import fr.istic.app.pool.persistence.jpa.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UpdatePoolUseCase {

    private final PoolRepository poolRepository;
    private final CompetenceRepository competenceRepository;

    @Transactional
    public Pool update(Long id, UpdatePoolUseCaseDto dto) {
        var pool = poolRepository.findById(id).orElseThrow(() -> new NotFoundException("Pool", id));
        pool.setNom(dto.nom);

        var poolCompetenceIds = pool.getCompetences()
                .stream()
                .map(competence -> competence.getCompetence().getId())
                .toList();

        //get all competences that are in the pool but not in the new competences list
        var competencesToRemove = poolCompetenceIds
                .stream()
                .filter(competenceId -> !dto.competenceIds.contains(competenceId))
                .map(competenceId -> competenceRepository.findById(competenceId).orElseThrow(() -> new NotFoundException("Competence", competenceId)))
                .toList();

        //get all competences that are in the new competences list but not in the pool
        var competencesToAdd = dto.competenceIds
                .stream()
                .filter(competenceId -> !poolCompetenceIds.contains(competenceId))
                .map(competenceId -> competenceRepository.findById(competenceId).orElseThrow(() -> new NotFoundException("Competence", competenceId)))
                .toList();

        //remove competences that are in the pool but not in the new competences list
        var newCompetencePoolRelationList = pool.getCompetences()
                .stream()
                .filter(matchPoolCompetence -> !competencesToRemove.contains(matchPoolCompetence.getCompetence()))
                .collect(Collectors.toList());

        //add competences that are in the new competences list but not in the pool
        newCompetencePoolRelationList.addAll(
                competencesToAdd
                        .stream()
                        .map(competence ->
                                MatchPoolCompetence
                                        .builder()
                                        .pool(pool)
                                        .competence(competence)
                                        .build()
                        )
                        .toList()
        );

        pool.setCompetences(newCompetencePoolRelationList);

        return poolRepository.save(pool);
    }


    public record UpdatePoolUseCaseDto(
            String nom,
            List<Long> competenceIds
    ) {
    }
}
