package bank.api.util;

import bank.api.dto.BankAccountsDto;
import bank.api.dto.CardsDto;
import bank.api.dto.ClientsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public ClientsDto toDto(@NonNull Clients clients) {
        return new ClientsDto(clients.getId(), clients.getName());
    }

    public CardsDto toDto(@NonNull Cards cards) {
        return new CardsDto(cards.getId(), cards.getNumber());
    }

    public BankAccountsDto toDto(@NonNull BankAccounts bankAccounts) {
        return new BankAccountsDto(bankAccounts.getId(), bankAccounts.getNumber(), bankAccounts.getBalance());
    }

    public Clients toEntity(@NonNull ClientsDto clientsDto) {
        return new Clients(clientsDto.getName());
    }

    public Cards toEntity(@NonNull CardsDto cardsDto) {
        return new Cards(cardsDto.getNumber());
    }

    public BankAccounts toEntity(@NonNull BankAccountsDto bankAccountsDto) {
        return new BankAccounts(bankAccountsDto.getNumber(), bankAccountsDto.getBalance());
    }

}
