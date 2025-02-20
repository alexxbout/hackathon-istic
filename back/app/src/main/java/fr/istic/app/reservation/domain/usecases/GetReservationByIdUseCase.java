package fr.istic.app.reservation.domain.usecases;


import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.reservation.domain.model.Reservation;
import fr.istic.app.reservation.persistence.jpa.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetReservationByIdUseCase {
    private final ReservationRepository reservationRepository;
    public Reservation getReservationById(Long id){
        return this.reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation", id));
    }
}
