package bank.api.utils;

import bank.api.dto.BankAccountsDto;
import bank.api.dto.CardsDto;
import bank.api.dto.ClientsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;

import java.util.Objects;

public final class ConverterDto {

	private ConverterDto() {

	}

	public static ClientsDto toDtoFromClients(Clients clients) {
		Objects.requireNonNull(clients, "clients cannot be null");
		return new ClientsDto(clients.getName());
	}

	public static CardsDto toDtoFromCards(Cards cards) {
		Objects.requireNonNull(cards, "cards cannot be null");
		return new CardsDto(cards.getJsonNumber());
	}

	public static BankAccountsDto toDtoFromBankAccounts(BankAccounts bankAccounts) {
		Objects.requireNonNull(bankAccounts, "bankAccounts cannot be null");
		return new BankAccountsDto(bankAccounts.getNumber(), bankAccounts.getBalance());
	}
}
