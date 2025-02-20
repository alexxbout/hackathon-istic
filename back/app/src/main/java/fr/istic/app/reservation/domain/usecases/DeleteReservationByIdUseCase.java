package fr.istic.app.reservation.domain.usecases;

import fr.istic.app.reservation.domain.model.Reservation;
import fr.istic.app.reservation.persistence.jpa.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DeleteReservationByIdUseCase {
    private final ReservationRepository reservationRepository;

    public void deleteReservationById(Long id){
        this.reservationRepository.deleteById(id);
    }

}
