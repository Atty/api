package bank.api.services;

import bank.api.dao.CardsDao;
import bank.api.entities.Cards;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final        CardsDao cardsDao;
    private static final Logger   LOGGER = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public Cards addCards(String bankAccountNumber) {
        LOGGER.debug("Start addCards method in CardsServiceImpl...");
        return cardsDao.addCards(bankAccountNumber);
    }

    @Override
    @Transactional
    public void removeCards(Cards card) {
        LOGGER.debug("Start removeCards method in CardsServiceImpl...");
        cardsDao.removeCards(card);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Cards> getCardsByClient(String clientsName) {
        LOGGER.debug("Start getCardsByClient method in CardsServiceImpl...");
        return cardsDao.getCardsByClient(clientsName);
    }

    @Override
    @Transactional
    public void addFundsByCard(long cardNumber, int value) {
        LOGGER.debug("Start addFundsByCard method in CardsServiceImpl...");
        cardsDao.addFundsByCard(cardNumber, value);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int checkBalance(long cardNumber) {
        LOGGER.debug("Start checkBalance method in CardsServiceImpl...");
        return cardsDao.checkBalance(cardNumber);
    }
}
