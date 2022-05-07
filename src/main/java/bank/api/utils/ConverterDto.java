package bank.api.utils;

import bank.api.dto.BankAccountsDto;
import bank.api.dto.CardsDto;
import bank.api.dto.ClientsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;


public class ConverterDto {

    public static ClientsDto toDto(Clients clients) {
        return new ClientsDto(clients.getName());
    }

    public static CardsDto toDto(Cards cards) {
        return new CardsDto(String.valueOf(cards.getNumber()));
    }

    public static BankAccountsDto toDto(BankAccounts bankAccounts) {
        return new BankAccountsDto(bankAccounts.getNumber(), bankAccounts.getBalance());
    }
}
