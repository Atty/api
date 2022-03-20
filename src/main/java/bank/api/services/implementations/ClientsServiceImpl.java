package bank.api.services.implementations;

import bank.api.entities.Clients;
import bank.api.exceptions.DataBaseException;
import bank.api.repository.ClientsRepo;
import bank.api.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientsServiceImpl implements ClientsService {

    private final        ClientsRepo clientsRepo;
    private static final Logger      logger = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public void addClient(Clients clients) {
        logger.debug("ClientsServiceImpl.addClient: add client " + clients);
        try {
            clientsRepo.save(clients);
        } catch (Exception e) {
            logger.error("ClientsServiceImpl.addClient: " + e.getMessage());
            throw new DataBaseException("Error during add client", e);
        }
    }

    @Override
    @Transactional
    public void deleteClients(Clients clients) {
        logger.debug("ClientsServiceImpl.deleteClient: delete client " + clients);
        try {
            clientsRepo.delete(clients);
        } catch (Exception e) {
            logger.error("ClientsServiceImpl.deleteClients: " + e.getMessage());
            throw new DataBaseException("Error during delete client", e);
        }
    }

    @Override
    public List<Clients> getListOfAllClients() {
        logger.debug("ClientsServiceImpl.getListOfAllClients: get all clients");
        try {
            return clientsRepo.findAll();
        } catch (Exception e) {
            logger.error("ClientsServiceImpl.getListOfAllClients: " + e.getMessage());
            throw new DataBaseException("Error during get all clients", e);
        }
    }

}
