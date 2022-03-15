package bank.api.services;

import bank.api.dao.BankAccountsDao;
import bank.api.entities.BankAccounts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountsServiceImpl implements BankAccountsService {

    private final        BankAccountsDao bankAccountsDao;
    private static final Logger          LOGGER = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public void addBankAccounts(int idClients, BankAccounts bankAccounts) {
        LOGGER.debug("Start addBankAccounts method in BankAccountsServiceImpl...");
        bankAccountsDao.addBankAccounts(idClients, bankAccounts);
    }

    @Override
    @Transactional
    public void removeBankAccounts(BankAccounts bankAccounts) {
        LOGGER.debug("Start removeBankAccounts method in BankAccountsServiceImpl...");
        bankAccountsDao.removeBankAccounts(bankAccounts);
    }
}
