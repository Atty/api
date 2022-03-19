package bank.api.services;

import bank.api.entities.BankAccounts;
import bank.api.entities.Clients;
import bank.api.exceptions.NotFoundException;
import bank.api.repository.BankAccountsRepo;
import bank.api.repository.ClientsRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountsServiceImpl implements BankAccountsService {

    private final        BankAccountsRepo bankAccountsRepo;
    private final        ClientsRepo      clientsRepo;
    private static final Logger           LOGGER = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public void addBankAccounts(long idClients, BankAccounts bankAccounts) {
        LOGGER.debug("Start addBankAccounts method in BankAccountsServiceImpl...");
        bankAccountsRepo.save(bankAccounts);
        Clients clients = clientsRepo.findById(idClients).orElseThrow(() -> new NotFoundException("Clients with id: " + idClients + " not found"));
        clients.addBankAccounts(bankAccounts);
    }

    @Override
    @Transactional
    public void removeBankAccounts(BankAccounts bankAccounts) {
        LOGGER.debug("Start removeBankAccounts method in BankAccountsServiceImpl...");
        bankAccountsRepo.delete(bankAccounts);
        bankAccounts.getClients().removeBankAccount(bankAccounts);
    }
}
