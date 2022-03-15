package bank.api.services;


import bank.api.entities.BankAccounts;

/**
 * For future extension
 */
public interface BankAccountsService {

    /**
     * @param idClients
     * @param bankAccounts
     */
    void addBankAccounts(int idClients, BankAccounts bankAccounts);

    /**
     * @param bankAccounts
     */
    void removeBankAccounts(BankAccounts bankAccounts);
}
