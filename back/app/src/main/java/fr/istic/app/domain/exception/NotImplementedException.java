package fr.istic.app.domain.exception;

import fr.istic.app.domain.exception.enums.ExceptionMessages;

public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
        super(ExceptionMessages.API_NOT_IMPLEMENTED_YET.getMessage());
    }
}
