package bank.api.services.implementations;

import bank.api.dto.CardsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import bank.api.repositories.BankAccountsRepo;
import bank.api.repositories.CardsRepo;
import bank.api.repositories.ClientsRepo;
import bank.api.services.CardsService;
import bank.api.utils.ConverterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bank.api.utils.Validator.*;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final CardsRepo        cardsRepo;
    private final BankAccountsRepo bankAccountsRepo;
    private final ClientsRepo      clientsRepo;

    @Override
    @Transactional
    public CardsDto addCards(String bankAccountNumber) {
        validateBankAccountNumber(bankAccountNumber);
        Cards        card         = cardsRepo.save(new Cards(cardsRepo.getMaxCardsNumber() + 1));
        BankAccounts bankAccounts = bankAccountsRepo.findBankAccountsByNumber(bankAccountNumber);
        bankAccounts.addCards(card);
        return ConverterDto.toDto(card);
    }

    @Override
    @Transactional
    public void removeCards(Cards cards) {
        cardsRepo.delete(cards);
        cards.getBankAccounts().removeCards(cards);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CardsDto> getCardsByClient(String clientsName) {
        Clients clients = clientsRepo.findClientsByName(clientsName);
        return clients.getBankAccountsList()
                .stream()
                .flatMap(n -> n.getCardsList().stream())
                .map(ConverterDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addFundsByCard(String cardNumber, String value) {
        validateCardNumber(cardNumber);
        int   checkedValue = validateValue(value);
        Cards cards        = cardsRepo.findCardsByNumber(cardNumber);
        cards.getBankAccounts().addMoney(checkedValue);
        cardsRepo.save(cards);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int checkBalance(String cardNumber) {
        validateCardNumber(cardNumber);
        Cards cards = cardsRepo.findCardsByNumber(cardNumber);
        return cards.getBankAccounts().getBalance();
    }

    @Override
    public List<CardsDto> getListOfAllCards() {
        return cardsRepo.findAll()
                .stream()
                .map(ConverterDto::toDto)
                .collect(Collectors.toList());
    }
}
