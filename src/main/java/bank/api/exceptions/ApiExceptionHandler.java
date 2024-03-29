package bank.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static bank.api.exceptions.ApiExceptionsHandlerUtils.createExceptionResponse;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = {IncorrectInputException.class})
	public ResponseEntity<Object> handleIncorrectInputException(IncorrectInputException e) {
		return createExceptionResponse(HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(value = {NotFoundException.class})
	public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
		return createExceptionResponse(HttpStatus.NOT_FOUND, e);
	}

	@ExceptionHandler(value = {WrongArgumentException.class})
	public ResponseEntity<Object> handleDataBaseException(WrongArgumentException e) {
		return createExceptionResponse(HttpStatus.BAD_REQUEST, e);
	}

}