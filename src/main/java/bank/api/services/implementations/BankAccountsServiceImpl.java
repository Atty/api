package bank.api.services.implementations;

import bank.api.entities.BankAccounts;
import bank.api.entities.Clients;
import bank.api.exceptions.DataBaseException;
import bank.api.exceptions.NotFoundException;
import bank.api.repository.BankAccountsRepo;
import bank.api.repository.ClientsRepo;
import bank.api.services.BankAccountsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountsServiceImpl implements BankAccountsService {

    private final        BankAccountsRepo bankAccountsRepo;
    private final        ClientsRepo      clientsRepo;
    private static final Logger           logger = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public void addBankAccounts(long idClients, BankAccounts bankAccounts) {
        logger.debug("BankAccountsServiceImpl.addBankAccounts: add new bank account " + bankAccounts + " for client with id " + idClients);
        try {
            bankAccountsRepo.save(bankAccounts);
            Clients clients = clientsRepo.findById(idClients).orElseThrow(() -> new NotFoundException("Clients with id: " + idClients + " not found"));
            clients.addBankAccounts(bankAccounts);
        } catch (NotFoundException e) {
            logger.error("BankAccountsServiceImpl.addBankAccounts: " + e.getMessage());
            throw new NotFoundException("Clients with id: " + idClients + " not found");
        } catch (Exception e) {
            logger.error("BankAccountsServiceImpl.addBankAccounts: " + e.getMessage());
            throw new DataBaseException("Error during add new bank account", e);
        }
    }

    @Override
    @Transactional
    public void removeBankAccounts(BankAccounts bankAccounts) {
        logger.debug("BankAccountsServiceImpl.removeBankAccounts: delete bank account " + bankAccounts);
        try {
            bankAccountsRepo.delete(bankAccounts);
            bankAccounts.getClients().removeBankAccount(bankAccounts);
        } catch (Exception e) {
            logger.error("BankAccountsServiceImpl.removeBankAccounts: " + e.getMessage());
            throw new DataBaseException("Error during delete bank account", e);
        }
    }

    @Override
    public List<BankAccounts> getListOfAllBankAccounts() {
        logger.debug("BankAccountsServiceImpl.getListOfAllBankAccounts: get all bank accounts");
        try {
            return bankAccountsRepo.findAll();
        } catch (Exception e) {
            logger.error("BankAccountsServiceImpl.getListOfAllBankAccounts: " + e.getMessage());
            throw new DataBaseException("Error during get all bank accounts", e);
        }
    }
}
