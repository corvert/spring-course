package edu.sda26.springcourse.exception;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TransactionNotFoundException extends Exception {

        private int errorCode;
        private String message;
        public TransactionNotFoundException(String message, int errorCode) {
            super(message);
            this.errorCode = errorCode;
            this.message = message;
        }
}
