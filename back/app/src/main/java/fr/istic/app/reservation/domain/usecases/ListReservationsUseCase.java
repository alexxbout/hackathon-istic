package fr.istic.app.reservation.domain.usecases;

import fr.istic.app.projet.domain.model.Projet;
import fr.istic.app.reservation.domain.model.Reservation;
import fr.istic.app.reservation.persistence.jpa.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ListReservationsUseCase {
    private final ReservationRepository reservationRepository;
   public List<Reservation> list() {
       return this.reservationRepository.findAll();
   }

}
