package fr.istic.app.common.domain.exception;

import fr.istic.app.common.domain.exception.enums.ExceptionMessages;

public class ReservationOverlapException extends RuntimeException {

    public ReservationOverlapException(String message) {
        super(String.format(ExceptionMessages.RESERVATION_DATES_OVERLAP.getMessage(), message));
    }
}
