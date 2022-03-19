package bank.api.util;

import bank.api.dto.BankAccountsDto;
import bank.api.dto.CardsDto;
import bank.api.dto.ClientsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import org.springframework.stereotype.Component;


public class Converter {

    public static ClientsDto toDto(Clients clients) {
        return new ClientsDto(clients.getId(), clients.getName());
    }

    public static CardsDto toDto(Cards cards) {
        return new CardsDto(cards.getId(), cards.getNumber());
    }

    public static BankAccountsDto toDto(BankAccounts bankAccounts) {
        return new BankAccountsDto(bankAccounts.getId(), bankAccounts.getNumber(), bankAccounts.getBalance());
    }

    public static Clients toEntity(ClientsDto clientsDto) {
        return new Clients(clientsDto.getName());
    }

    public static Cards toEntity(CardsDto cardsDto) {
        return new Cards(cardsDto.getNumber());
    }

    public static BankAccounts toEntity(BankAccountsDto bankAccountsDto) {
        return new BankAccounts(bankAccountsDto.getNumber(), bankAccountsDto.getBalance());
    }

}
