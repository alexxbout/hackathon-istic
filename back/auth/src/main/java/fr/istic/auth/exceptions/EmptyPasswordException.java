package fr.istic.auth.exceptions;

public class EmptyPasswordException extends RuntimeException{
    public EmptyPasswordException(String message) {
        super(message);
    }
}
