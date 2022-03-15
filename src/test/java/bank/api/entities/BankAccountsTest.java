package bank.api.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountsTest {

    private final Cards        cards        = new Cards();
    private final BankAccounts bankAccounts = new BankAccounts();

    @Test
    void addCards() {
        bankAccounts.addCards(cards);
        bankAccounts.addCards(cards);
        assertEquals(2, bankAccounts.getCardsList().size());
        assertEquals(bankAccounts, cards.getBankAccounts());
    }

    @Test
    void removeCards() {
        bankAccounts.addCards(cards);
        bankAccounts.addCards(cards);
        bankAccounts.removeCards(cards);
        assertEquals(1, bankAccounts.getCardsList().size());
    }
}