package fr.istic.app.common.domain.exception;

import fr.istic.app.common.domain.exception.enums.ExceptionMessages;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
        super(ExceptionMessages.NOT_FOUND.getMessage());
    }

    public NotFoundException(String ressourceName, Long id) {
        super(String.format(ExceptionMessages.NOT_FOUND.getMessage(), ressourceName, id));
    }
}
