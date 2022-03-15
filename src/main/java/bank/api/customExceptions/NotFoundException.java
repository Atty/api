package bank.api.customExceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public NotFoundException(String errorCode, String errorMessage) {
        super();
        this.errorCode    = errorCode;
        this.errorMessage = errorMessage;
    }
}
