package fr.istic.auth.exceptions.handlers;

import fr.istic.auth.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Define a simple error response object
    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }
        // Getters and setters (or use Lombok @Data for brevity)
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return new ErrorResponse("User Already Exists", ex.getMessage());
    }

    @ExceptionHandler(UserNotCompleteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserNotComplete(UserNotCompleteException ex) {
        return new ErrorResponse("User Not Complete", ex.getMessage());
    }

    @ExceptionHandler(EmptyPasswordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmptyPassword(EmptyPasswordException ex) {
        return new ErrorResponse("No password", ex.getMessage());
    }


    @ExceptionHandler(EmptyPictureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmptyPicture(EmptyPictureException ex) {
        return new ErrorResponse("No picture", ex.getMessage());
    }


    @ExceptionHandler(InvalidRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidRole(InvalidRoleException ex) {
        return new ErrorResponse("Invalid Role", ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException ex) {
        return new ErrorResponse("User Not Found", ex.getMessage());
    }

    // Catch-all exception handler (optional)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        return new ErrorResponse("Internal Error", ex.getMessage());
    }
}
