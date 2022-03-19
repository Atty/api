package bank.api.services.implementations;

import bank.api.entities.Clients;
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
        logger.debug("Start addClient method in ClientsServiceImpl...");
        clientsRepo.save(clients);
    }

    @Override
    @Transactional
    public void deleteClients(Clients clients) {
        logger.debug("Start deleteClient method in ClientsServiceImpl...");
        clientsRepo.delete(clients);
    }

    @Override
    public List<Clients> getListOfAllClients() {
        logger.debug("Start getListOfAllClients method in ClientsServiceImpl...");
        return clientsRepo.findAll();
    }

}
