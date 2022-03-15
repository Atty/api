package bank.api.dao;

import bank.api.entities.Clients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ClientsDaoImpl implements ClientsDao {

    @PersistenceContext
    private              EntityManager entityManager;
    private static final Logger        LOGGER = LoggerFactory.getLogger(ClientsDaoImpl.class);

    @Override
    public void addClient(Clients clients) {
        LOGGER.debug("Start addClient method in ClientsDaoImpl...");
        entityManager.persist(clients);
    }

    @Override
    public void deleteClients(Clients clients) {
        LOGGER.debug("Start deleteClients method in ClientsDaoImpl...");
        entityManager.remove(clients);
    }

}
