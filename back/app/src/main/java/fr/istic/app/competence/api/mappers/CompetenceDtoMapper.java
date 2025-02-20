package fr.istic.app.competence.api.mappers;

import fr.istic.app.competence.api.dtos.CompetenceDto;
import fr.istic.app.competence.domain.model.Competence;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CompetenceDtoMapper {

    CompetenceDtoMapper INSTANCE = Mappers.getMapper(CompetenceDtoMapper.class);

    Competence fromDto(CompetenceDto competenceDto);
    CompetenceDto toDto(Competence competence);

    List<Competence> fromDto(List<CompetenceDto> competenceDtos);
    List<CompetenceDto> toDto(List<Competence> competences);


}
