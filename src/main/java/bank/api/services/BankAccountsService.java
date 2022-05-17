package bank.api.services;


import bank.api.dto.BankAccountsDto;
import bank.api.entities.BankAccounts;

import java.util.List;

public interface BankAccountsService {

    /**
     * Данный метод позволяет добавить новый счет в базу данных
     * привязывая его к определенному клиенту по его идентификатору
     *
     * @param bankAccountsDto счет, который нужно добавить
     */
    String addBankAccount(BankAccountsDto bankAccountsDto);

    /**
     * Данный метод позволяет удалить существующий счет из базы данных
     *
     * @param bankAccountsDto счет, который нужно удалить
     */
    String deleteBankAccount(BankAccountsDto bankAccountsDto);

    /**
     * Данный метод позволяет получить список всех счетов в базе данных
     *
     * @return Список всех счетов
     */
    List<BankAccountsDto> getListOfAllBankAccounts();
}
