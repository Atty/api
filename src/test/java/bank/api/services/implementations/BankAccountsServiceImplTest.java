package bank.api.services.implementations;

import bank.api.dto.BankAccountsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Clients;
import bank.api.exceptions.NotFoundException;
import bank.api.repositories.BankAccountsRepo;
import bank.api.repositories.ClientsRepo;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountsServiceImplTest {

	@Mock
	private BankAccountsRepo bankAccountsRepo;
	@Mock
	private ClientsRepo      clientsRepo;

	@InjectMocks
	private BankAccountsServiceImpl bankAccountsService;


	@DisplayName("Тест добавления аккаунта к существующему клиенту")
	@Test
	public void addBankAccountToExistClientTest() {
		// given
		val bankAccount = mock(BankAccounts.class);
		val client      = mock(Clients.class);
		val bankAccountDto = BankAccountsDto.builder()
											.clientName("Test")
											.balance(123)
											.number("1233123412341234")
											.build();

		// when
		when(bankAccountsRepo.save(any(BankAccounts.class))).thenReturn(bankAccount);
		when(clientsRepo.findClientsByName(anyString())).thenReturn(Optional.of(client));

		// then
		val result = bankAccountsService.addBankAccount(bankAccountDto);

		verify(bankAccountsRepo).save(any(BankAccounts.class));
		verify(clientsRepo).findClientsByName(anyString());
		verify(client).addBankAccounts(any(BankAccounts.class));

		assertEquals("Банковский аккаунт для \"Test\" успешно добавлен", result);
	}

	@DisplayName("Тест добавления аккаунта когда клиента нет")
	@Test
	public void addBankAccountToNonExistClientTest() {
		// given
		val bankAccountDto = BankAccountsDto.builder()
											.clientName("Test")
											.balance(123)
											.number("1233123412341234")
											.build();

		// when
		when(clientsRepo.findClientsByName(anyString())).thenReturn(Optional.empty());

		// then
		assertThatThrownBy(() -> bankAccountsService.addBankAccount(bankAccountDto))
				.isInstanceOf(NotFoundException.class)
				.hasMessageContaining(format("Clients with id: \"%s\" not found", bankAccountDto.getClientName()));
	}

	@DisplayName("Тест успешного удаления аккаунта")
	@Test
	public void deleteExistBankAccountTest() {
		// given
		val bankAccounts = mock(BankAccounts.class);
		val client       = mock(Clients.class);
		val bankAccountDto = BankAccountsDto.builder()
											.clientName("Test")
											.balance(123)
											.number("1233123412341234")
											.build();

		// when
		when(bankAccountsRepo.findBankAccountsByNumber(anyString())).thenReturn(Optional.of(bankAccounts));
		when(bankAccounts.getClient()).thenReturn(client);

		// then
		val result = bankAccountsService.deleteBankAccount(bankAccountDto);

		verify(bankAccountsRepo).delete(any(BankAccounts.class));
		verify(client).removeBankAccount(any(BankAccounts.class));

		assertEquals(result, "Банковский аккаунт успешно удален!");
	}

	@DisplayName("Тест удаления несуществующего аккаунта")
	@Test
	public void deleteNonExistBankAccountTest() {
		// given
		val bankAccountDto = BankAccountsDto.builder()
											.clientName("Test")
											.balance(123)
											.number("1233123412341234")
											.build();

		// when
		when(bankAccountsRepo.findBankAccountsByNumber(anyString())).thenReturn(Optional.empty());

		// then
		assertThatThrownBy(() -> bankAccountsService.deleteBankAccount(bankAccountDto))
				.isInstanceOf(NotFoundException.class)
				.hasMessageContaining(format("BankAccount with number: \"%s\" not found", bankAccountDto.getNumber()));
	}

	@DisplayName("Тест получения всех аккаунтов")
	@Test
	public void getListOfAllBankAccountsTest() {
		// given
		val bankAccounts = mock(BankAccounts.class);

		// when
		when(bankAccountsRepo.findAll()).thenReturn(List.of(bankAccounts));

		// then
		val result = bankAccountsService.getListOfAllBankAccounts();

		assertEquals(result.size(), 1);
	}

}