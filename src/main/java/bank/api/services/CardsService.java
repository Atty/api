package bank.api.services;

import bank.api.dto.CardsDto;

import java.util.List;

public interface CardsService {

    /**
     * Данный метод позволяет добавить новую карту к определенному счету
     *
     * @param bankAccountNumber Номер счета к которому добавляется карта
     * @return созданную карту при успешном добавлении
     */
    CardsDto addCardsToBankAccount(String bankAccountNumber);

    /**
     * Данный метод позволяет удалить существующую карту из базы данных
     *
     * @param cardsDto карта, которую нужно удалить (нужно указать только номер карты)
     */
    String removeCard(CardsDto cardsDto);

    /**
     * Данный метод позволяет получить список карт, которые принадлежат
     * определенному клиенту
     *
     * @param clientsName имя клиента, чьи карты нужно получить
     * @return список карт указанного клиента
     */
    List<CardsDto> getAllCardsByClient(String clientsName);

    /**
     * Данный метод пополняет баланс счета к которому привязана карта
     * на указанную сумму
     *
     * @param cardsDto карта на которую нужно добавить средства (указать номер и количество средств)
     * @return сообщение о выполнении операции
     */
    String addFundsOnCard(CardsDto cardsDto);

    /**
     * Данный метод позволяет проверить баланс средств на счете к которому привязана
     * указанная карта
     *
     * @param cardNumber номер карты с помощью которой необходимо узнать баланс
     * @return сумма средств на счете в данный момент
     */
    int checkBalance(String cardNumber);

    /**
     * Данный метод позволяет получить список всех карт
     *
     * @return список всех карт
     */
    List<CardsDto> getListOfAllCards();
}
