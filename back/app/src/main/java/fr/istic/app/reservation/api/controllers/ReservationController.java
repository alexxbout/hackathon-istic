package fr.istic.app.reservation.api.controllers;


import fr.istic.app.reservation.api.dtos.ReservationDto;
import fr.istic.app.reservation.api.mappers.ReservationDtoMapper;
import fr.istic.app.reservation.domain.model.Reservation;
import fr.istic.app.reservation.domain.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reservations", produces = "application/json", consumes = "application/json")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Operations about reservations")
public class ReservationController {

    private final ListReservationsUseCase listReservationsUseCase;
    private final ListReservationsByProfilIdUseCase listReservationsByProfilIdUseCase;
    private final CreateReservationUseCase createReservationUseCase;
    private final CreateReservationsUseCase createReservationsUseCase;
    private final GetReservationByIdUseCase getReservationByIdUseCase;
    private final DeleteReservationByIdUseCase deleteReservationByIdUseCase;
    private final ReservationDtoMapper reservationDtoMapper;

    @GetMapping
    @Operation(summary = "Get all reservations; if profilId is provided, get projets by profilId")
    public ResponseEntity<List<ReservationDto>> getReservations(@PathVariable(required = false) Long profilId) {

        List<Reservation> reservations;
        if (profilId == null) {
            reservations = this.listReservationsUseCase.list();
        } else{
            reservations = this.listReservationsByProfilIdUseCase.list(profilId);
        }

        var mapped = this.reservationDtoMapper.toDtos(reservations);

        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a reservation by id")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {
        var found = this.getReservationByIdUseCase.getReservationById(id);
        var mapped = this.reservationDtoMapper.toDto(found);
        return ResponseEntity.ok(mapped);
    }

    @PostMapping
    @Operation(summary = "Create a reservation")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody CreateReservationUseCase.CreateReservationUseCaseDto reservationDto) {
        var createdReservation = createReservationUseCase.create(reservationDto);
        var mapped = reservationDtoMapper.toDto(createdReservation);
        return ResponseEntity.ok(mapped);
    }

    @PostMapping("/list")
    @Operation(summary = "Create a list of reservations")
    public ResponseEntity<List<ReservationDto>> createReservations(@RequestBody List<CreateReservationUseCase.CreateReservationUseCaseDto> reservationsDto) {
        var createdReservations = createReservationsUseCase.create(reservationsDto);
        var mapped = reservationDtoMapper.toDtos(createdReservations);
        return ResponseEntity.ok(mapped);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a reservation by id")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        this.deleteReservationByIdUseCase.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }
}
