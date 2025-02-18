package fr.istic.app.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BasicException extends RuntimeException {
    public BasicException(String message) {
        super(message);
    }
}
