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

import static bank.api.utils.Validator.validateClientsName;

@Service
@RequiredArgsConstructor
public class ClientsServiceImpl implements ClientsService {

	private final ClientsRepo clientsRepo;

	@Override
	@Transactional
	public String addClient(ClientsDto clientsDto) {
		var clientName = clientsDto.getName();
		validateClientsName(clientName);
		var client = new Clients(clientName);
		clientsRepo.save(client);
		return "Клиент успешно добавлен!";
	}

	@Override
	@Transactional
	public String deleteClient(ClientsDto clientsDto) {
		var clientName = clientsDto.getName();
		validateClientsName(clientName);
		var client = clientsRepo.findClientsByName(clientName)
								.orElseThrow(() -> new NotFoundException(String.format("Clients with name: \"%s\" not found", clientName)));
		clientsRepo.delete(client);
		return "Клиент успешно удален!";
	}

	@Override
	public List<ClientsDto> getListOfAllClients() {
		return clientsRepo.findAll()
						  .stream()
						  .map(ConverterDto::toDtoFromClients)
						  .collect(Collectors.toList());
	}

}
