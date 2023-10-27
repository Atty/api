package bank.api.services.implementations;

import bank.api.dto.BankAccountsDto;
import bank.api.entities.BankAccounts;
import bank.api.exceptions.NotFoundException;
import bank.api.repositories.BankAccountsRepo;
import bank.api.repositories.ClientsRepo;
import bank.api.services.BankAccountsService;
import bank.api.utils.ConverterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bank.api.utils.Validator.validateBankAccountNumber;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BankAccountsServiceImpl implements BankAccountsService {

	private final BankAccountsRepo bankAccountsRepo;
	private final ClientsRepo      clientsRepo;

	@Override
	@Transactional
	public String addBankAccount(BankAccountsDto bankAccountsDto) {
		var bankAccountNumber = bankAccountsDto.getNumber();
		validateBankAccountNumber(bankAccountNumber);
		var clientName  = bankAccountsDto.getClientName();
		var bankAccount = new BankAccounts(bankAccountNumber);
		clientsRepo.findClientsByName(bankAccountsDto.getClientName())
				   .orElseThrow(() -> new NotFoundException(format("Clients with id: \"%s\" not found", clientName)))
				   .addBankAccounts(bankAccount);
		bankAccountsRepo.save(bankAccount);
		return format("Банковский аккаунт для \"%s\" успешно добавлен", clientName);
	}

	@Override
	@Transactional
	public String deleteBankAccount(BankAccountsDto bankAccountsDto) {
		var bankAccountNumber = bankAccountsDto.getNumber();
		validateBankAccountNumber(bankAccountNumber);
		var bankAccount = bankAccountsRepo.findBankAccountsByNumber(bankAccountNumber)
										  .orElseThrow(() -> new NotFoundException(format("BankAccount with number: \"%s\" not found", bankAccountNumber)));
		bankAccountsRepo.delete(bankAccount);
		bankAccount.getClient().removeBankAccount(bankAccount);
		return "Банковский аккаунт успешно удален!";
	}

	@Override
	public List<BankAccountsDto> getListOfAllBankAccounts() {
		return bankAccountsRepo.findAll()
							   .stream()
							   .map(ConverterDto::toDtoFromBankAccounts)
							   .collect(Collectors.toList());
	}
}
