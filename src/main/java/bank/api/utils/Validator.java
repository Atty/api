package bank.api.utils;

import bank.api.exceptions.IncorrectInputException;

import java.util.regex.Pattern;

public class Validator {

	private static final Pattern CARD_NUMBER_PATTERN  = Pattern.compile("^1[0-9]{15}$");
	private static final Pattern BANK_ACCOUNT_PATTERN = Pattern.compile("^1[0-9]{15}$");
	private static final Pattern CLIENTS_NAME_PATTERN = Pattern.compile("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");

	public static void validateCardNumber(String number) {
		if (!CARD_NUMBER_PATTERN.matcher(number).matches()) {
			throw new IncorrectInputException(String.format("Invalid card number: %s", number));
		}
	}

	public static int validateFunds(String value) {
		final int check;
		try {
			check = Integer.parseInt(value);
			if (check <= 0) {
				throw new IncorrectInputException(String.format("Invalid value: %s", value));
			}
		} catch (NumberFormatException e) {
			throw new IncorrectInputException(String.format("Invalid value: %s", value), e);
		}
		return check;
	}

	public static void validateBankAccountNumber(String number) {
		if (!BANK_ACCOUNT_PATTERN.matcher(number).matches()) {
			throw new IncorrectInputException(String.format("Invalid bank account number: %s", number));
		}
	}

	public static void validateClientsName(String name) {
		if (name.isEmpty()) {
			throw new IncorrectInputException("Client name cannot be empty");
		}
		if (!CLIENTS_NAME_PATTERN.matcher(name).matches()) {
			throw new IncorrectInputException(String.format("Invalid client's name: %s. Name should contain only alphabets and spaces, with each word starting with an uppercase letter and followed by one or more lowercase letters.", name));
		}
	}

}
