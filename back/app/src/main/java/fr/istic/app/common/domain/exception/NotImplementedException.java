package fr.istic.app.common.domain.exception;

import fr.istic.app.common.domain.exception.enums.ExceptionMessages;

public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
        super(ExceptionMessages.API_NOT_IMPLEMENTED_YET.getMessage());
    }
}
