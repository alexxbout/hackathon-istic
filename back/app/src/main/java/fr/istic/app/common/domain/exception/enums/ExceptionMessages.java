package fr.istic.app.common.domain.exception.enums;
public enum ExceptionMessages {

    NOT_FOUND("Resource %s with id= %s not found"),
    CV_NOT_FOUND("CV not found for profilId= %s"),
    API_NOT_IMPLEMENTED_YET("API endpoint is not implemented yet"),
    RESERVATION_DATES_OVERLAP("Reservation dates overlap with existing reservations: %s"),
    PROFILE_EXISTS_IN_RESERVATION("Profile  %s %s exists in reservation."),
    SEARCH_CRITERIA_ALL_NULL("All search criteria are null, must be at least one search criteria valid"),;

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
