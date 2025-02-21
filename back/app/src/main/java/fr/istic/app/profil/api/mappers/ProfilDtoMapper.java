package fr.istic.app.profil.api.mappers;

import fr.istic.app.competence.api.dtos.CompetenceDto;
import fr.istic.app.competence.api.mappers.CompetenceDtoMapper;
import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import fr.istic.app.profil.api.dtos.ProfilDto;
import fr.istic.app.profil.domain.model.Profil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(uses = {CompetenceDtoMapper.class})
public interface ProfilDtoMapper {

    @Mapping(target = "siteAddr.ville", source = "ville")
    @Mapping(target = "competences", ignore = true)
    @Mapping(target = "cv.id", source = "cv_id")
    Profil fromDto(ProfilDto profilDto);

    @Mapping(target = "competences", qualifiedByName = "competencesToDto")
    @Mapping(target = "ville", source = "siteAddr.ville")
    @Mapping(target = "cv_id", source = "cv.id")
    ProfilDto toDto(Profil profil);

    List<Profil> fromDto(List<ProfilDto> profilDtos);
    List<ProfilDto> toDto(List<Profil> profils);

    @Named("competencesToDto")
    default List<CompetenceDto> competencesToDto(List<MatchProfilCompetence> competences) {
        if (competences == null) { // ✅ Vérification pour éviter NullPointerException
            return List.of();
        }

        var competenceDtoMapper = CompetenceDtoMapper.INSTANCE;

        return competences.stream()
                .map(competence -> competenceDtoMapper.toDto(competence.getCompetence()))
                .toList();
    }


}
