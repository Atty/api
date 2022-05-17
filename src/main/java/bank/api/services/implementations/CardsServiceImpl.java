package bank.api.services.implementations;

import bank.api.dto.CardsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import bank.api.exceptions.NotFoundException;
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
    public CardsDto addCardsToBankAccount(String bankAccountNumber) {
        validateBankAccountNumber(bankAccountNumber);
        Cards card = cardsRepo.save(new Cards(cardsRepo.getMaxCardsNumber() + 1));
        BankAccounts bankAccounts = bankAccountsRepo.findBankAccountsByNumber(bankAccountNumber)
                .orElseThrow(() -> new NotFoundException("BankAccount with number: \"" + bankAccountNumber + "\" not found"));
        bankAccounts.addCards(card);
        return ConverterDto.toDto(card);
    }

    @Override
    @Transactional
    public String removeCard(CardsDto cardsDto) {
        validateCardNumber(cardsDto.getNumber());
        Cards card = cardsRepo.findCardsByNumber(cardsDto.getNumber())
                .orElseThrow(() -> new NotFoundException("Cards with number: \"" + cardsDto.getNumber() + "\" not found"));
        cardsRepo.delete(card);
        card.getBankAccounts().removeCards(card);
        return "Карта успешно удалена!";
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CardsDto> getAllCardsByClient(String clientsName) {
        Clients clients = clientsRepo.findClientsByName(clientsName)
                .orElseThrow(() -> new NotFoundException("Clients with name: \"" + clientsName + "\" not found"));
        return clients.getBankAccountsList()
                .stream()
                .flatMap(n -> n.getCardsList().stream())
                .map(ConverterDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String addFundsOnCard(CardsDto cardsDto) {
        validateCardNumber(cardsDto.getNumber());
        int checkedValue = validateFunds(cardsDto.getFunds());
        Cards cards = cardsRepo.findCardsByNumber(cardsDto.getNumber())
                .orElseThrow(() -> new NotFoundException("Card with number: \"" + cardsDto.getNumber() + "\" not found"));
        cards.getBankAccounts().addMoney(checkedValue);
        cardsRepo.save(cards);
        return "Счет успешно пополнен!";
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int checkBalance(String cardNumber) {
        validateCardNumber(cardNumber);
        Cards cards = cardsRepo.findCardsByNumber(cardNumber)
                .orElseThrow(() -> new NotFoundException("Card with number: \"" + cardNumber + "\" not found"));
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
