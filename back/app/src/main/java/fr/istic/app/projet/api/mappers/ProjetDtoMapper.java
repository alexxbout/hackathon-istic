package fr.istic.app.projet.api.mappers;

import fr.istic.app.projet.api.dtos.ProjetDto;
import fr.istic.app.projet.domain.model.Projet;
import fr.istic.app.reservation.api.mappers.ReservationDtoMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {ReservationDtoMapper.class})
public interface ProjetDtoMapper {

    Projet fromDto(ProjetDto projetDto);
    ProjetDto toDto(Projet projet);

    List<Projet> fromDto(List<ProjetDto> projetDtos);
    List<ProjetDto> toDto(List<Projet> projets);
}
