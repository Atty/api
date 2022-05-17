package bank.api.services.implementations;

import bank.api.dto.ClientsDto;
import bank.api.entities.Clients;
import bank.api.exceptions.NotFoundException;
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
    public String addClient(ClientsDto clientsDto) {
        clientsRepo.save(new Clients(clientsDto.getName()));
        return "Клиент успешно добавлен!";
    }

    @Override
    @Transactional
    public String deleteClient(ClientsDto clientsDto) {
        Clients client = clientsRepo.findClientsByName(clientsDto.getName())
                .orElseThrow(() -> new NotFoundException("Clients with name: \"" + clientsDto.getName() + "\" not found"));
        clientsRepo.delete(client);
        return "Клиент успешно удален!";
    }

    @Override
    public List<ClientsDto> getListOfAllClients() {
        return clientsRepo.findAll()
                .stream()
                .map(ConverterDto::toDto)
                .collect(Collectors.toList());
    }

}
