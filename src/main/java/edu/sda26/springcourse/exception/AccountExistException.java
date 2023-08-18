package edu.sda26.springcourse.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountExistException extends Exception {

    private int errorCode;
    private String message;
    public AccountExistException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
