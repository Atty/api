package bank.api.services;

import bank.api.dto.CardsDto;
import bank.api.entities.Cards;

import java.util.List;

public interface CardsService {

    /**
     * Данный метод позволяет добавить новую карту к определенному счету
     *
     * @param bankAccountNumber Счет к которому добавляется карта
     * @return метод возвращает созданную карту при успешном добавлении
     */
    CardsDto addCards(String bankAccountNumber);

    /**
     * Данный метод позволяет удалить существующую карту из базы данных
     *
     * @param card карта, которую нужно удалить
     */
    void removeCards(Cards card);

    /**
     * Данный метод позволяет получить список карт, которые принадлежат
     * определенному клиенту
     *
     * @param clientsName имя клиента, чьи карты нужно получить
     * @return возвращает лист карт указанного клиента
     */
    List<CardsDto> getCardsByClient(String clientsName);

    /**
     * Данный метод пополняет баланс счета к которому привязана карта
     * на указанную сумму
     *
     * @param cardNumber карта через которую происходит пополение
     * @param value      сумма на которую необходимо пополнить
     */
    void addFundsByCard(String cardNumber, String value);

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
