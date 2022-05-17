package bank.api.services;

import bank.api.dto.CardsDto;

import java.util.List;
//TODO дополнить описание
public interface CardsService {

    /**
     * Данный метод позволяет добавить новую карту к определенному счету
     *
     * @param bankAccountNumber Счет к которому добавляется карта
     * @return метод возвращает созданную карту при успешном добавлении
     */
    CardsDto addCardsToBankAccount(String bankAccountNumber);

    /**
     * Данный метод позволяет удалить существующую карту из базы данных
     *
     * @param cardsDto карта, которую нужно удалить
     */
    String removeCard(CardsDto cardsDto);

    /**
     * Данный метод позволяет получить список карт, которые принадлежат
     * определенному клиенту
     *
     * @param clientsName имя клиента, чьи карты нужно получить
     * @return возвращает лист карт указанного клиента
     */
    List<CardsDto> getAllCardsByClient(String clientsName);

    /**
     * Данный метод пополняет баланс счета к которому привязана карта
     * на указанную сумму
     *
     */
    String addFundsOnCard(CardsDto cardsDto);

    /**
     * Данный метод позволяет проверить баланс средств на счете к которому привязана
     * указанная карта
     *
     * @param cardNumber карта с помощью котороый необходимо узнать баланс
     * @return возвращается сумма которая находится на счете в данный момент
     */
    int checkBalance(String cardNumber);

    /**
     * Данный метод позволяет получить список всех карт
     *
     * @return список всех карт
     */
    List<CardsDto> getListOfAllCards();
}
