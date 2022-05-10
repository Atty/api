package bank.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongArgumentException extends RuntimeException {

    public WrongArgumentException(String message) {
        super(message);
    }

    public WrongArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
