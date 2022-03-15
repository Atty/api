package bank.api.dao;

import bank.api.entities.BankAccounts;
import bank.api.entities.Clients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BankAccountsDaoImpl implements BankAccountsDao {

    @PersistenceContext
    private              EntityManager entityManager;
    private static final Logger        LOGGER = LoggerFactory.getLogger(ClientsDaoImpl.class);

    @Override
    public void addBankAccounts(int idClients, BankAccounts bankAccounts) {
        LOGGER.debug("Start addBankAccounts method in BankAccountDaoImpl...");
        Clients clients = entityManager
                .createQuery("from Clients where id =: idClients", Clients.class)
                .setParameter("idClients", idClients)
                .getSingleResult();
        entityManager.persist(bankAccounts);
        clients.addBankAccounts(bankAccounts);
    }

    @Override
    public void removeBankAccounts(BankAccounts bankAccounts) {
        LOGGER.debug("Start removeBankAccounts method in BankAccountDaoImpl...");
        entityManager.remove(bankAccounts);
        bankAccounts.getClients().removeBankAccount(bankAccounts);
    }

}
