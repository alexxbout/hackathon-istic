package fr.istic.app.reservation.domain.usecases;

import fr.istic.app.reservation.domain.model.Reservation;
import fr.istic.app.reservation.persistence.jpa.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListReservationsByProfilIdUseCase {

    private final ReservationRepository reservationRepository;

    public List<Reservation> list(Long profilId) {
        return this.reservationRepository.findAllByProfilId(profilId);
    }
}
