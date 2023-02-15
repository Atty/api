package bank.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ApiExceptionsHandlerUtils {

	public static ResponseEntity<Object> createExceptionResponse(HttpStatus status, Throwable e) {
		ResponseExceptionObject reo = new ResponseExceptionObject(e.getMessage(), status, ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<>(reo, status);
	}

}