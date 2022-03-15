package bank.api.customExceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class IncorrectInputException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public IncorrectInputException(String errorCode, String errorMessage) {
        super();
        this.errorCode    = errorCode;
        this.errorMessage = errorMessage;
    }
}
