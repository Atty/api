package bank.api.services.implementations;

import bank.api.dto.CardsDto;
import bank.api.entities.Cards;
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
import static java.lang.String.format;

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
		var card = cardsRepo.save(new Cards(cardsRepo.getMaxCardsNumber() + 1L));
		bankAccountsRepo.findBankAccountsByNumber(bankAccountNumber)
						.orElseThrow(() -> new NotFoundException(format("BankAccount with number: \"%s\" not found", bankAccountNumber)))
						.addCards(card);
		return ConverterDto.toDtoFromCards(card);
	}

	@Override
	@Transactional
	public String removeCard(CardsDto cardsDto) {
		var cardNumber = cardsDto.getNumber();
		validateCardNumber(cardNumber);
		var card = cardsRepo.findCardsByNumber(cardNumber)
							.orElseThrow(() -> new NotFoundException(format("Cards with number: \"%s\" not found", cardNumber)));
		cardsRepo.delete(card);
		card.getBankAccount().removeCard(card);
		return "Карта успешно удалена!";
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CardsDto> getAllCardsByClient(String clientsName) {
		return clientsRepo.findClientsByName(clientsName)
						  .orElseThrow(() -> new NotFoundException(format("Clients with name: \"%s\" not found", clientsName)))
						  .getBankAccountsList()
						  .stream()
						  .flatMap(n -> n.getCardsList().stream())
						  .map(ConverterDto::toDtoFromCards)
						  .collect(Collectors.toList());
	}

	@Override
	@Transactional
	public String addFundsOnCard(CardsDto cardsDto) {
		var cardNumber = cardsDto.getNumber();
		validateCardNumber(cardNumber);
		var checkedValue = validateFunds(cardsDto.getFunds());
		var card = cardsRepo.findCardsByNumber(cardNumber)
							.orElseThrow(() -> new NotFoundException(format("Card with number: \"%s\" not found", cardNumber)));
		card.getBankAccount().addMoney(checkedValue);
		cardsRepo.save(card);
		return "Счет успешно пополнен!";
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int checkBalance(String cardNumber) {
		validateCardNumber(cardNumber);
		return cardsRepo.findCardsByNumber(cardNumber)
						.orElseThrow(() -> new NotFoundException(format("Card with number: \"%s\" not found", cardNumber)))
						.getBankAccount()
						.getBalance();
	}

	@Override
	public List<CardsDto> getListOfAllCards() {
		return cardsRepo.findAll()
						.stream()
						.map(ConverterDto::toDtoFromCards)
						.collect(Collectors.toList());
	}

}