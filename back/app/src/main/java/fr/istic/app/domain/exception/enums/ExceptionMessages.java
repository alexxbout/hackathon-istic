package fr.istic.app.domain.exception.enums;
public enum ExceptionMessages {

    API_NOT_IMPLEMENTED_YET("API endpoint is not implemented yet");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
