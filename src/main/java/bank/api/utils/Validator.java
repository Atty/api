package bank.api.utils;

import bank.api.exceptions.IncorrectInputException;

public class Validator {

    public static void validateCardNumber(long number) {
        if (number < 0L || number > 1_0000_0000_0000_0000L) {
            throw new IncorrectInputException("Incorrect card number");
        }
    }

    public static void validateValue(int value) {
        if (value < 0) {
            throw new IncorrectInputException("Incorrect value");
        }
    }

    public static void validateBankAccountNumber(long number) {
        if (number < 0L || number > 1_0000_0000_0000_0000L) {
            throw new IncorrectInputException("Incorrect bank account number");
        }
    }
}
