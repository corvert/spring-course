package edu.sda26.springcourse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error message: "
                + customerNotFoundException.getMessage()
                + " Error code: " + customerNotFoundException.getErrorCode());
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error message: "
                + accountNotFoundException.getMessage()
                + " Error code: " + accountNotFoundException.getErrorCode());
    }
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handleTransactionNotFoundException(TransactionNotFoundException transactionNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error message: "
                + transactionNotFoundException.getMessage()
                + " Error code: " + transactionNotFoundException.getErrorCode());
    }
    @ExceptionHandler(AccountExistException.class)
    public ResponseEntity<String> handleAccountExitsException(AccountExistException accountExistException){
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("Error message: "
        + accountExistException.getMessage() + " Error code: " + accountExistException.getErrorCode());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction amount exceeded Account Balance");
    }
}
