package fr.istic.app.common.domain.exception.handler;

import fr.istic.app.common.domain.exception.*;
import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.common.domain.exception.NotImplementedException;
import fr.istic.app.common.domain.exception.ProfilExistsInReservationException;
import fr.istic.app.common.domain.exception.ReservationOverlapException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<Map<String, String>> handleNotImplementedException(NotImplementedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationOverlapException.class)
    public ResponseEntity<Map<String, String>> handleReservationException(ReservationOverlapException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfilExistsInReservationException.class)
    public ResponseEntity<Map<String, String>> handleProfilExistsInReservationException(ProfilExistsInReservationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SearchParamsAllNull.class)
    public ResponseEntity<Map<String, String>> handleSearchParamsAllNull(SearchParamsAllNull ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
