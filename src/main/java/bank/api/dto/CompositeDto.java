package bank.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Этот класс только для необычных запросов от клиента
 * когда нужно получить 2 поля из разных классов
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompositeDto {

    private String clientName;
    private String accountNumber;
    private int    amountOfMoney; //сумма которую клиент хочет положить на счет
    private long   cardNumber;
}
