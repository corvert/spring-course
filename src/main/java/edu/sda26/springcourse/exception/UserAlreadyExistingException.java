package edu.sda26.springcourse.exception;

public class UserAlreadyExistingException extends Exception{
    private int errorCode;
    private String message;
    public UserAlreadyExistingException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
