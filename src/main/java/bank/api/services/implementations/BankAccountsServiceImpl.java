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

@Service
@RequiredArgsConstructor
public class BankAccountsServiceImpl implements BankAccountsService {

    private final BankAccountsRepo bankAccountsRepo;
    private final ClientsRepo      clientsRepo;

    @Override
    @Transactional
    public String addBankAccount(BankAccountsDto bankAccountsDto) {
        validateBankAccountNumber(bankAccountsDto.getNumber());
        BankAccounts bankAccount = new BankAccounts(bankAccountsDto.getNumber());
        bankAccountsRepo.save(bankAccount);
        clientsRepo.findClientsByName(bankAccountsDto.getClientName())
                .orElseThrow(() -> new NotFoundException("Clients with id: \"" + bankAccountsDto.getClientName() + "\" not found"))
                .addBankAccounts(bankAccount);
        return "Банковский аккаунт для \"" + bankAccountsDto.getClientName() + "\" успешно добавлен";
    }

    @Override
    @Transactional
    public String deleteBankAccount(BankAccountsDto bankAccountsDto) {
        validateBankAccountNumber(bankAccountsDto.getNumber());
        BankAccounts bankAccount = bankAccountsRepo.findBankAccountsByNumber(bankAccountsDto.getNumber())
                .orElseThrow(() -> new NotFoundException("BankAccount with number: \"" + bankAccountsDto.getNumber() + "\" not found"));
        bankAccountsRepo.delete(bankAccount);
        bankAccount.getClient().removeBankAccount(bankAccount);
        return "Банковский аккаунт успешно удален!";
    }

    @Override
    public List<BankAccountsDto> getListOfAllBankAccounts() {
        return bankAccountsRepo.findAll()
                .stream()
                .map(ConverterDto::toDto)
                .collect(Collectors.toList());
    }
}
