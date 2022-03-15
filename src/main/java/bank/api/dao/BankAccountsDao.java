package bank.api.dao;

import bank.api.entities.BankAccounts;

public interface BankAccountsDao {

    /**
     * Данный метод позволяет добавить новый счет в базу данных
     * привязывая его к определенному клиенту по его идентификатору
     *
     * @param idClients    идентификатор клиента которому надо добавить Счет
     * @param bankAccounts счет, который нужно добавить
     */
    void addBankAccounts(int idClients, BankAccounts bankAccounts);

    /**
     * Данный метод позволяет удалить существующий счет из базы данных
     *
     * @param bankAccounts счет, который нужно удалить
     */
    void removeBankAccounts(BankAccounts bankAccounts);

}
