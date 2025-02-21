package fr.istic.app.common.domain.exception;

import fr.istic.app.common.domain.exception.enums.ExceptionMessages;

public class ProfilExistsInReservationException extends RuntimeException {
    public ProfilExistsInReservationException(String nomProfil, String PrenomProfil) {
        super(String.format(ExceptionMessages.PROFILE_EXISTS_IN_RESERVATION.getMessage(), nomProfil, PrenomProfil));
    }
}