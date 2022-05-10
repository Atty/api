package bank.api.services.implementations;

import bank.api.dto.BankAccountsDto;
import bank.api.entities.BankAccounts;
import bank.api.entities.Clients;
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

@Service
@RequiredArgsConstructor
public class BankAccountsServiceImpl implements BankAccountsService {

    private final BankAccountsRepo bankAccountsRepo;
    private final ClientsRepo      clientsRepo;

    @Override
    @Transactional
    public void addBankAccounts(long idClients, BankAccounts bankAccounts) {
        bankAccountsRepo.save(bankAccounts);
        Clients clients = clientsRepo.findById(idClients)
                .orElseThrow(() -> new NotFoundException("Clients with id: " + idClients + " not found"));
        clients.addBankAccounts(bankAccounts);
    }

    @Override
    @Transactional
    public void removeBankAccounts(BankAccounts bankAccounts) {
        bankAccountsRepo.delete(bankAccounts);
        bankAccounts.getClients().removeBankAccount(bankAccounts);
    }

    @Override
    public List<BankAccountsDto> getListOfAllBankAccounts() {
        return bankAccountsRepo.findAll()
                .stream()
                .map(ConverterDto::toDto)
                .collect(Collectors.toList());
    }
}
