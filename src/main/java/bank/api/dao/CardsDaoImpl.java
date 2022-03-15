package bank.api.dao;


import bank.api.customExceptions.IncorrectInputException;
import bank.api.customExceptions.NotFoundException;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CardsDaoImpl implements CardsDao {

    @PersistenceContext
    private              EntityManager entityManager;
    private static final Logger        LOGGER = LoggerFactory.getLogger(CardsDaoImpl.class);

    @Override
    public Cards addCards(String bankAccountNumber) {
        LOGGER.debug("Start addCards method in CardsDaoImpl...");
        try {
            Long.parseLong(bankAccountNumber);
        } catch (Exception e) {
            LOGGER.error("AddCards error, incorrect number", e);
            throw new IncorrectInputException();
        }
        try {
            Cards card = entityManager
                    .createQuery("from Cards where id = (select max(id) from Cards)", Cards.class)
                    .getSingleResult();
            BankAccounts bankAccounts = entityManager
                    .createQuery("from BankAccounts where number =: bankAccountNumber", BankAccounts.class)
                    .setParameter("bankAccountNumber", bankAccountNumber)
                    .getSingleResult();
            Cards newCard = new Cards(card.getNumber() + 1);
            entityManager.persist(newCard);
            bankAccounts.addCards(newCard);
            return newCard;
        } catch (Exception e) {
            LOGGER.error("AddCards error, not found", e);
            throw new NotFoundException();
        }
    }

    @Override
    public void removeCards(Cards card) {
        LOGGER.debug("Start removeCards method in CardsDaoImpl...");
        try {
            entityManager.remove(card);
            card.getBankAccounts().removeCards(card);
        } catch (Exception e) {
            LOGGER.error("RemoveCards error", e);
            e.printStackTrace();
        }
    }

    @Override
    public List<Cards> getCardsByClient(String clientsName) {
        LOGGER.debug("Start getCardsByClient method in CardsDaoImpl...debug");
        try {
            Clients clients = entityManager
                    .createQuery("from Clients where name =: clientsName", Clients.class)
                    .setParameter("clientsName", clientsName)
                    .getSingleResult();
            return clients.getBankAccountsList()
                    .stream()
                    .flatMap(n -> n.getCardsList().stream())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("GetCardsByClient error, not found", e);
            throw new NotFoundException();
        }
    }

    @Override
    public void addFundsByCard(long cardNumber, int value) {
        LOGGER.debug("Start addFundsByCards method in CardsDaoImpl...");
        if (cardNumber < 0 || value < 0) throw new IncorrectInputException();
        try {
            Cards card = entityManager.createQuery("from Cards where number =: cardNumber", Cards.class)
                    .setParameter("cardNumber", cardNumber)
                    .getSingleResult();
            card.getBankAccounts().setBalance(card.getBankAccounts().getBalance() + value);
            entityManager.merge(card);
        } catch (Exception e) {
            LOGGER.error("AddFundsByCards error, not found", e);
            throw new NotFoundException();
        }
    }

    @Override
    public int checkBalance(long cardNumber) {
        LOGGER.debug("Start checkBalance method in CardsDaoImpl...");
        if (cardNumber < 0) throw new IncorrectInputException();
        try {
            Cards card = entityManager
                    .createQuery("from Cards where number =: cardNumber", Cards.class)
                    .setParameter("cardNumber", cardNumber)
                    .getSingleResult();
            return card.getBankAccounts().getBalance();
        } catch (Exception e) {
            LOGGER.error("CheckBalance error, not found", e);
            throw new NotFoundException();
        }
    }
}
