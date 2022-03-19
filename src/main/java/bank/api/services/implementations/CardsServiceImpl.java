package bank.api.services.implementations;

import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import bank.api.repository.BankAccountsRepo;
import bank.api.repository.CardsRepo;
import bank.api.repository.ClientsRepo;
import bank.api.services.CardsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bank.api.util.Validator.*;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final        CardsRepo        cardsRepo;
    private final        BankAccountsRepo bankAccountsRepo;
    private final        ClientsRepo      clientsRepo;
    private static final Logger           logger = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public Cards addCards(long bankAccountNumber) {
        logger.debug("Start addCards method in CardsServiceImpl...");
        validateBankAccountNumber(bankAccountNumber);
        Cards cards = new Cards(cardsRepo.getMaxCardsNumber() + 1);
        cardsRepo.save(cards);
        BankAccounts bankAccounts = bankAccountsRepo.findBankAccountsByNumber(String.valueOf(bankAccountNumber));
        bankAccounts.addCards(cards);
        return cards;
    }

    @Override
    @Transactional
    public void removeCards(Cards cards) {
        logger.debug("Start removeCards method in CardsServiceImpl...");
        cardsRepo.delete(cards);
        cards.getBankAccounts().removeCards(cards);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Cards> getCardsByClient(String clientsName) {
        logger.debug("Start getCardsByClient method in CardsServiceImpl...");
        Clients clients = clientsRepo.findClientsByName(clientsName);
        return clients.getBankAccountsList()
                .stream()
                .flatMap(n -> n.getCardsList().stream())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addFundsByCard(long cardNumber, int value) {
        logger.debug("Start addFundsByCard method in CardsServiceImpl...");
        validateCardNumber(cardNumber);
        validateValue(value);
        Cards cards = cardsRepo.findCardsByNumber(cardNumber);
        cards.getBankAccounts().addMoney(value);
        cardsRepo.save(cards);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int checkBalance(long cardNumber) {
        logger.debug("Start checkBalance method in CardsServiceImpl...");
        validateCardNumber(cardNumber);
        Cards cards = cardsRepo.findCardsByNumber(cardNumber);
        return cards.getBankAccounts().getBalance();
    }

    @Override
    public List<Cards> getListOfAllCards() {
        logger.debug("Start getListOfAllCards method in CardsServiceImpl...");
        return cardsRepo.findAll();
    }
}
