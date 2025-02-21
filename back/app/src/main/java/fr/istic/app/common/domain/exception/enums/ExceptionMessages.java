package fr.istic.app.common.domain.exception.enums;
public enum ExceptionMessages {

    NOT_FOUND("Resource %s with id= %s not found"),
    API_NOT_IMPLEMENTED_YET("API endpoint is not implemented yet"),
    RESERVATION_DATES_OVERLAP("Reservation dates overlap with existing reservations: %s"),
    PROFILE_EXISTS_IN_RESERVATION("Profile  %s %s exists in reservation."),;

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
