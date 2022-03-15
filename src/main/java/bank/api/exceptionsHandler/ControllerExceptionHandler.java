package bank.api.exceptionsHandler;

import bank.api.customExceptions.IncorrectInputException;
import bank.api.customExceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {IncorrectInputException.class})
    public ResponseEntity<String> handleIncorrectInput(IncorrectInputException iie) {

        return new ResponseEntity<>("Incorrect input!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<String> handleNotFound(NotFoundException nfe) {

        return new ResponseEntity<>("Object not found!", HttpStatus.NOT_FOUND);
    }

}