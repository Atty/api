package bank.api.services;

import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import bank.api.exceptions.IncorrectInputException;
import bank.api.repository.BankAccountsRepo;
import bank.api.repository.CardsRepo;
import bank.api.repository.ClientsRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final        CardsRepo        cardsRepo;
    private final        BankAccountsRepo bankAccountsRepo;
    private final        ClientsRepo      clientsRepo;
    private static final Logger           LOGGER = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public Cards addCards(String bankAccountNumber) {
        LOGGER.debug("Start addCards method in CardsServiceImpl...");
        try {
            Long.parseLong(bankAccountNumber);
        } catch (Exception e) {
            LOGGER.error("AddCards error, incorrect number", e);
            throw new IncorrectInputException("Incorrect bankAccountNumber");
        }
        Cards cards = new Cards(cardsRepo.getMaxCardsNumber() + 1);
        cardsRepo.save(cards);
        BankAccounts bankAccounts = bankAccountsRepo.findBankAccountsByNumber(bankAccountNumber);
        bankAccounts.addCards(cards);
        return cards;
    }

    @Override
    @Transactional
    public void removeCards(Cards cards) {
        LOGGER.debug("Start removeCards method in CardsServiceImpl...");
        cardsRepo.delete(cards);
        cards.getBankAccounts().removeCards(cards);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Cards> getCardsByClient(String clientsName) {
        LOGGER.debug("Start getCardsByClient method in CardsServiceImpl...");
        Clients clients = clientsRepo.findClientsByName(clientsName);
        List<Cards> allClientsCards = clients.getBankAccountsList()
                .stream()
                .flatMap(n -> n.getCardsList().stream())
                .collect(Collectors.toList());
        return allClientsCards;
    }

    @Override
    @Transactional
    public void addFundsByCard(long cardNumber, int value) {
        LOGGER.debug("Start addFundsByCard method in CardsServiceImpl...");
        if (cardNumber < 0) throw new IncorrectInputException("Incorrect cardNumber");
        if (value < 0) throw new IncorrectInputException("Incorrect value");
        Cards cards = cardsRepo.findCardsByNumber(cardNumber);
        cards.getBankAccounts().setBalance(cards.getBankAccounts().getBalance() + value);
        cardsRepo.save(cards);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int checkBalance(long cardNumber) {
        LOGGER.debug("Start checkBalance method in CardsServiceImpl...");
        if (cardNumber < 0) throw new IncorrectInputException("Incorrect cardNumber");
        Cards cards = cardsRepo.findCardsByNumber(cardNumber);
        return cards.getBankAccounts().getBalance();
    }
}
