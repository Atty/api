package bank.api.services;


import bank.api.dto.BankAccountsDto;

import java.util.List;

public interface BankAccountsService {

    /**
     * Данный метод позволяет добавить новый счет в базу данных
     * привязывая его к определенному клиенту по его идентификатору
     *
     * @param bankAccountsDto счет, который нужно добавить (нужно указать только номер)
     * @return сообщение о выполнении операции
     */
    String addBankAccount(BankAccountsDto bankAccountsDto);

    /**
     * Данный метод позволяет удалить существующий счет из базы данных
     *
     * @param bankAccountsDto счет, который нужно удалить (нужно указать только номер)
     * @return сообщение о выполнении операции
     */
    String deleteBankAccount(BankAccountsDto bankAccountsDto);

    /**
     * Данный метод позволяет получить список всех счетов в базе данных
     *
     * @return Список всех счетов
     */
    List<BankAccountsDto> getListOfAllBankAccounts();
}
