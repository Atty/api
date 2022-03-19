package bank.api.services;

import bank.api.entities.Clients;
import bank.api.repository.ClientsRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientsServiceImpl implements ClientsService {

    private final        ClientsRepo clientsRepo;
    private static final Logger      LOGGER = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public void addClient(Clients clients) {
        LOGGER.debug("Start addClient method in ClientsServiceImpl...");
        clientsRepo.save(clients);
    }

    @Override
    @Transactional
    public void deleteClients(Clients clients) {
        LOGGER.debug("Start deleteClient method in ClientsServiceImpl...");
        clientsRepo.delete(clients);
    }

}
