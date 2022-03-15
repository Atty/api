package bank.api.services;

import bank.api.entities.Cards;

import java.util.List;

/**
 * For future extension
 */
public interface CardsService {

    /**
     * @param bankAccountNumber
     * @return
     * @throws Exception
     */
    Cards addCards(String bankAccountNumber);

    /**
     * @param card
     */
    void removeCards(Cards card);

    /**
     * @param clientsName
     * @return
     */
    List<Cards> getCardsByClient(String clientsName);

    /**
     * @param cardNumber
     * @param value
     * @throws Exception
     */
    void addFundsByCard(long cardNumber, int value);

    /**
     * @param cardNumber
     * @return
     * @throws Exception
     */
    int checkBalance(long cardNumber);
}
