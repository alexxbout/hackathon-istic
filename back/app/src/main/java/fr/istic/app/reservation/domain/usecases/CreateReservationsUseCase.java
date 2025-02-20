package fr.istic.app.reservation.domain.usecases;

import fr.istic.app.reservation.domain.model.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CreateReservationsUseCase {
    private final CreateReservationUseCase createReservationUseCase;

    public List<Reservation> create(List<CreateReservationUseCase.CreateReservationUseCaseDto> createReservationsUseCaseDtos) {
        List<Reservation> reservations = new ArrayList<>();
        for (CreateReservationUseCase.CreateReservationUseCaseDto createReservationsUseCaseDto : createReservationsUseCaseDtos) {
            reservations.add(this.createReservationUseCase.create(createReservationsUseCaseDto));
        }
        return reservations;
    }
}
