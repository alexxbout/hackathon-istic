package fr.istic.auth.exceptions;

public class UserNotCompleteException extends RuntimeException{
    public UserNotCompleteException(String message){
        super(message);
    }
}
