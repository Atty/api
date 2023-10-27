package bank.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountsDto {

    private String number;
    private int    balance;
    private String clientName;

    public BankAccountsDto(String number, int balance) {
        this.number  = number;
        this.balance = balance;
    }
}
