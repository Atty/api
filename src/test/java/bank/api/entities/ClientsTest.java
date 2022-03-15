package bank.api.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientsTest {

    private final Clients      clients      = new Clients();
    private final BankAccounts bankAccounts = new BankAccounts();

    @Test
    void testAddBankAccounts() {
        clients.addBankAccounts(bankAccounts);
        clients.addBankAccounts(bankAccounts);
        assertEquals(2, clients.getBankAccountsList().size());
        assertEquals(bankAccounts.getClients(), clients);
    }

    @Test
    void testRemoveBankAccount() {
        clients.addBankAccounts(bankAccounts);
        clients.addBankAccounts(bankAccounts);
        clients.removeBankAccount(bankAccounts);
        assertEquals(1, clients.getBankAccountsList().size());
    }
}