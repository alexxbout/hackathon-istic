package fr.istic.app.reservation.domain.usecases;

import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.common.domain.exception.ReservationOverlapException;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import fr.istic.app.projet.persistence.jpa.ProjetRepository;
import fr.istic.app.reservation.domain.model.Reservation;
import fr.istic.app.reservation.persistence.jpa.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final ProjetRepository projetRepository;
    private final ProfilRepository profilRepository;

    public Reservation create(CreateReservationUseCaseDto dto) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(dto.dateDebut());
        reservation.setDateFin(dto.dateFin());

        var projet = projetRepository.findById(dto.projetId()).orElseThrow(() -> new NotFoundException("Projet", dto.projetId()));
        reservation.setProjet(projet);
        var profil = profilRepository.findById(dto.profilId()).orElseThrow(() -> new NotFoundException("Profil", dto.profilId()));
        reservation.setProfil(profil);

        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservationsByProfilId(dto.profilId(), dto.dateDebut(), dto.dateFin());
        if (!overlappingReservations.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            var id = reservation.getId();
            var dateDebut = reservation.getDateDebut().format(formatter);
            var dateFin = reservation.getDateFin().format(formatter);
            var message = String.format("id=%s, dateDebut=%s, dateFin=%s", id, dateDebut, dateFin);

            throw new ReservationOverlapException(message);
        }

        return reservationRepository.save(reservation);
    }


    public record CreateReservationUseCaseDto(
            Long projetId,
            Long profilId,
            LocalDate dateDebut,
            LocalDate dateFin
    ) {
    }
}
