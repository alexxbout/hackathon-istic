package fr.istic.app.reservation.api.mappers;

import fr.istic.app.profil.api.mappers.ProfilDtoMapper;
import fr.istic.app.reservation.api.dtos.ReservationDto;
import fr.istic.app.reservation.domain.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {ProfilDtoMapper.class})
public interface ReservationDtoMapper {

    @Mapping(target = "projetId", source = "projet.id")
    ReservationDto toDto(Reservation reservation);

    @Mapping(target = "projet.id", source = "projetId")
    Reservation fromDto(ReservationDto reservationDto);

    List<ReservationDto> toDtos(List<Reservation> reservations);
    List<Reservation> fromDto(List<ReservationDto> reservationDtos);

}
