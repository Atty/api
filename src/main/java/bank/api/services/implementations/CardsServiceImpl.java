package bank.api.services.implementations;

import bank.api.dto.CardsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import bank.api.exceptions.DataBaseException;
import bank.api.repositories.BankAccountsRepo;
import bank.api.repositories.CardsRepo;
import bank.api.repositories.ClientsRepo;
import bank.api.services.CardsService;
import bank.api.utils.ConverterDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bank.api.utils.Validator.*;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final        CardsRepo        cardsRepo;
    private final        BankAccountsRepo bankAccountsRepo;
    private final        ClientsRepo      clientsRepo;
    private static final Logger           logger = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public CardsDto addCards(long bankAccountNumber) {
        logger.debug("CardsServiceImpl.addCards: add card for bank account " + bankAccountNumber);
        validateBankAccountNumber(bankAccountNumber);
        try {
            Cards        card         = cardsRepo.save(new Cards(cardsRepo.getMaxCardsNumber() + 1));
            BankAccounts bankAccounts = bankAccountsRepo.findBankAccountsByNumber(String.valueOf(bankAccountNumber));
            bankAccounts.addCards(card);
            return ConverterDto.toDto(card);
        } catch (Exception e) {
            logger.error("CardsServiceImpl.addCards: " + e.getMessage());
            throw new DataBaseException("Error during add card", e);
        }
    }

    @Override
    @Transactional
    public void removeCards(Cards cards) {
        logger.debug("CardsServiceImpl.removeCards: delete card " + cards);
        try {
            cardsRepo.delete(cards);
            cards.getBankAccounts().removeCards(cards);
        } catch (Exception e) {
            logger.error("CardsServiceImpl.removeCards: " + e.getMessage());
            throw new DataBaseException("Error during delete card", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CardsDto> getCardsByClient(String clientsName) {
        logger.debug("CardsServiceImpl.getCardsByClient: get all client cards with clients name " + clientsName);
        try {
            Clients clients = clientsRepo.findClientsByName(clientsName);
            return clients.getBankAccountsList()
                    .stream()
                    .flatMap(n -> n.getCardsList().stream())
                    .map(ConverterDto::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("CardsServiceImpl.getCardsByClient: " + e.getMessage());
            throw new DataBaseException("Error during get all cards", e);
        }
    }

    @Override
    @Transactional
    public void addFundsByCard(long cardNumber, int value) {
        logger.debug("CardsServiceImpl.addFundsByCard: put money(" + value + ") on card " + cardNumber);
        validateCardNumber(cardNumber);
        validateValue(value);
        try {
            Cards cards = cardsRepo.findCardsByNumber(cardNumber);
            cards.getBankAccounts().addMoney(value);
            cardsRepo.save(cards);
        } catch (Exception e) {
            logger.error("CardsServiceImpl.addFundsByCard: " + e.getMessage());
            throw new DataBaseException("Error during put money on card", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int checkBalance(long cardNumber) {
        logger.debug("CardsServiceImpl.checkBalance: check balance on card " + cardNumber);
        validateCardNumber(cardNumber);
        try {
            Cards cards = cardsRepo.findCardsByNumber(cardNumber);
            return cards.getBankAccounts().getBalance();
        } catch (Exception e) {
            logger.error("CardsServiceImpl.checkBalance: " + e.getMessage());
            throw new DataBaseException("Error during check balance", e);
        }
    }

    @Override
    public List<CardsDto> getListOfAllCards() {
        logger.debug("CardsServiceImpl.getListOfAllCards: get all cards");
        try {
            return cardsRepo.findAll().stream()
                    .map(ConverterDto::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("CardsServiceImpl.getListOfAllCards: " + e.getMessage());
            throw new DataBaseException("Error during get all cards", e);
        }
    }
}
