package bank.api.services;

import bank.api.dao.ClientsDao;
import bank.api.entities.Clients;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientsServiceImpl implements ClientsService {

    private final @NonNull ClientsDao clientsDao;
    private static final   Logger     LOGGER = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Override
    @Transactional
    public void addClient(Clients clients) {
        LOGGER.debug("Start addClient method in ClientsServiceImpl...");
        clientsDao.addClient(clients);
    }

    @Override
    @Transactional
    public void deleteClients(Clients clients) {
        LOGGER.debug("Start deleteClient method in ClientsServiceImpl...");
        clientsDao.deleteClients(clients);
    }

}
