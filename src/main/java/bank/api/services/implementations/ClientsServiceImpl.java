package bank.api.services.implementations;

import bank.api.dto.ClientsDto;
import bank.api.entities.Clients;
import bank.api.repositories.ClientsRepo;
import bank.api.services.ClientsService;
import bank.api.utils.ConverterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientsServiceImpl implements ClientsService {

    private final ClientsRepo clientsRepo;

    @Override
    @Transactional
    public void addClient(Clients clients) {
        clientsRepo.save(clients);
    }

    @Override
    @Transactional
    public void deleteClients(Clients clients) {
        clientsRepo.delete(clients);
    }

    @Override
    public List<ClientsDto> getListOfAllClients() {
        return clientsRepo.findAll()
                .stream()
                .map(ConverterDto::toDto)
                .collect(Collectors.toList());
    }

}
