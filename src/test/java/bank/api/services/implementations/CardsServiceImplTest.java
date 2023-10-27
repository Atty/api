package bank.api.services.implementations;

import bank.api.dto.CardsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Cards;
import bank.api.entities.Clients;
import bank.api.exceptions.NotFoundException;
import bank.api.repositories.BankAccountsRepo;
import bank.api.repositories.CardsRepo;
import bank.api.repositories.ClientsRepo;
import bank.api.utils.ConverterDto;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardsServiceImplTest {

	@Mock
	private CardsRepo        cardsRepo;
	@Mock
	private BankAccountsRepo bankAccountsRepo;
	@Mock
	private ClientsRepo      clientsRepo;

	@InjectMocks
	private CardsServiceImpl cardsService;

	@Test
	void addCardsToBankAccountTest() {
		val card        = new Cards(2L);
		val bankAccount = mock(BankAccounts.class);
		val number      = "1234123412341234";

		when(cardsRepo.getMaxCardsNumber()).thenReturn(1L);
		when(cardsRepo.save(any(Cards.class))).thenReturn(card);
		when(bankAccountsRepo.findBankAccountsByNumber(anyString())).thenReturn(Optional.of(bankAccount));

		val actual = cardsService.addCardsToBankAccount(number);

		verify(cardsRepo).getMaxCardsNumber();
		verify(cardsRepo).save(any(Cards.class));
		verify(bankAccountsRepo).findBankAccountsByNumber(anyString());
		verify(bankAccount).addCards(any(Cards.class));

		assertEquals(actual.getNumber(), "0000 0000 0000 0002");
	}

	@Test
	void addCardsToNonExistBankAccountTest() {
		val number = "1234123412341234";

		when(bankAccountsRepo.findBankAccountsByNumber(anyString())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> cardsService.addCardsToBankAccount(number));
	}

	@Test
	void removeCardTest() {
		val card        = mock(Cards.class);
		val bankAccount = mock(BankAccounts.class);
		val cardDto     = CardsDto.builder().number("1234123412341234").build();
		val response    = "Карта успешно удалена!";

		when(cardsRepo.findCardsByNumber(anyString())).thenReturn(Optional.of(card));
		when(card.getBankAccount()).thenReturn(bankAccount);

		val actual = cardsService.removeCard(cardDto);

		verify(cardsRepo, Mockito.times(1)).delete(card);

		assertEquals(actual, response);
	}

	@Test
	void removeCardThrownExceptionTest() {
		val cardDto = CardsDto.builder().number("1234123412341234").build();

		when(cardsRepo.findCardsByNumber(anyString())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> cardsService.removeCard(cardDto));
	}

	@Test
	void getAllCardsByClientTest() {
		val bankAccount = mock(BankAccounts.class);
		val client      = mock(Clients.class);
		val card        = new Cards(1234123412341234L);
		val cardDto     = ConverterDto.toDtoFromCards(card);

		when(clientsRepo.findClientsByName(anyString())).thenReturn(Optional.of(client));
		when(client.getBankAccountsList()).thenReturn(List.of(bankAccount));
		when(bankAccount.getCardsList()).thenReturn(List.of(card));

		val actual = cardsService.getAllCardsByClient("test");

		assertEquals(cardDto, actual.get(0));
	}

	@Test
	void getAllCardsByClientThrownExceptionTest() {
		when(clientsRepo.findClientsByName(anyString())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> cardsService.getAllCardsByClient("test"));
	}

	@Test
	void addFundsOnCardTest() {
		val card        = mock(Cards.class);
		val bankAccount = new BankAccounts("1234123412341234", 0);
		val cardDto     = CardsDto.builder().number("1234123412341235").funds("1000").build();

		when(cardsRepo.findCardsByNumber(anyString())).thenReturn(Optional.of(card));
		when(card.getBankAccount()).thenReturn(bankAccount);

		val actual = cardsService.addFundsOnCard(cardDto);

		assertEquals("Счет успешно пополнен!", actual);
		assertEquals(1000, bankAccount.getBalance());
	}

	@Test
	void addFundsOnCardThrownExceptionTest() {
		val cardDto = CardsDto.builder().number("1234123412341234").funds("1").build();

		when(cardsRepo.findCardsByNumber(anyString())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> cardsService.addFundsOnCard(cardDto));
	}

	@Test
	void checkBalanceTest() {
		val card        = mock(Cards.class);
		val cardNumber  = "1234123412341234";
		val bankAccount = new BankAccounts(cardNumber, 155);

		when(cardsRepo.findCardsByNumber(anyString())).thenReturn(Optional.of(card));
		when(card.getBankAccount()).thenReturn(bankAccount);

		val actual = cardsService.checkBalance(cardNumber);

		assertEquals(155, actual);
	}

	@Test
	void checkBalanceThrownExceptionTest() {
		val cardNumber = "1234123412341234";

		when(cardsRepo.findCardsByNumber(anyString())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> cardsService.checkBalance(cardNumber));
	}

	@Test
	void getListOfAllCardsTest() {
		val card    = mock(Cards.class);
		val cardDto = ConverterDto.toDtoFromCards(card);

		when(cardsRepo.findAll()).thenReturn(List.of(card));

		val actual = cardsService.getListOfAllCards();

		assertEquals(cardDto, actual.get(0));
	}

}