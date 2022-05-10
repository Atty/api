package bank.api.utils;

import bank.api.exceptions.IncorrectInputException;

public class Validator {

    public static void validateCardNumber(String number) {
        validateCardAndAccountNumber(number, "Incorrect card number: " + number);
    }

    public static int validateValue(String value) {
        int check = 0;
        try {
            check = Integer.parseInt(value);
            if (check <= 0) {
                throw new IncorrectInputException("Incorrect value: " + value);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("Incorrect value: " + value, e);
        }
        return check;
    }

    public static void validateBankAccountNumber(String number) {
        validateCardAndAccountNumber(number, "Incorrect bank account number: " + number);
    }

    private static void validateCardAndAccountNumber(String number, String error) {
        try {
            long check = Long.parseLong(number);
            if (check < 1000_0000_0000_0000L || check >= 1_0000_0000_0000_0000L) {
                throw new IncorrectInputException(error);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectInputException(error, e);
        }
    }
}
