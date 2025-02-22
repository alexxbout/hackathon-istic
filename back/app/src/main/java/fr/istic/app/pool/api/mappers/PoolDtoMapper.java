package fr.istic.app.pool.api.mappers;

import fr.istic.app.competence.api.dtos.CompetenceDto;
import fr.istic.app.competence.api.mappers.CompetenceDtoMapper;
import fr.istic.app.matchPoolCompetence.domain.model.MatchPoolCompetence;
import fr.istic.app.pool.api.dtos.PoolDto;
import fr.istic.app.pool.domain.model.Pool;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = CompetenceDtoMapper.class)
public interface PoolDtoMapper {

    @Mapping(target = "competences", qualifiedByName = "competenceListToDtoList")
    PoolDto toDto(Pool pool);

    List<PoolDto> toDtos(List<Pool> pools);

    @Named("competenceListToDtoList")
    default List<CompetenceDto> competenceListToDtoList(List<MatchPoolCompetence> matchPoolCompetences) {

        if (matchPoolCompetences == null) {
            return List.of();
        }
        var competenceInstance = CompetenceDtoMapper.INSTANCE;

        return matchPoolCompetences.stream()
                .map(MatchPoolCompetence::getCompetence)
                .map(competenceInstance::toDto)
                .collect(Collectors.toList());
    }

}
