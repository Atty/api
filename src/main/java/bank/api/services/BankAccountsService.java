package bank.api.services;


import bank.api.dto.BankAccountsDto;
import bank.api.entities.BankAccounts;

import java.util.List;

public interface BankAccountsService {

    /**
     * Данный метод позволяет добавить новый счет в базу данных
     * привязывая его к определенному клиенту по его идентификатору
     *
     * @param idClients    идентификатор клиента которому надо добавить Счет
     * @param bankAccounts счет, который нужно добавить
     */
    void addBankAccounts(long idClients, BankAccounts bankAccounts);

    /**
     * Данный метод позволяет удалить существующий счет из базы данных
     *
     * @param bankAccounts счет, который нужно удалить
     */
    void removeBankAccounts(BankAccounts bankAccounts);

    /**
     * Данный метод позволяет получить список всех счетов в базе данных
     *
     * @return Список всех счетов
     */
    List<BankAccountsDto> getListOfAllBankAccounts();
}
