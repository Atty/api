package bank.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompositeDto {

    private String clientName;
    private long   accountNumber;
    private int    amountOfMoney; //сумма которую клиент хочет положить на счет
    private long   cardNumber;
}
