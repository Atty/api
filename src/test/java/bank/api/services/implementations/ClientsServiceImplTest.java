package bank.api.services.implementations;

import bank.api.dto.ClientsDto;
import bank.api.entities.Clients;
import bank.api.exceptions.NotFoundException;
import bank.api.repositories.ClientsRepo;
import bank.api.utils.ConverterDto;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientsServiceImplTest {

	@Mock
	private ClientsRepo clientsRepo;

	@InjectMocks
	private ClientsServiceImpl clientsService;

	@Test
	void addClientTest() {
		val clientDto = ClientsDto.builder()
								  .name("Test")
								  .build();
		val response = "Клиент успешно добавлен!";

		val actual = clientsService.addClient(clientDto);

		verify(clientsRepo).save(any(Clients.class));

		assertEquals(response, actual);
	}

	@Test
	void deleteClientTest() {
		val client    = new Clients("Test");
		val clientDto = ConverterDto.toDtoFromClients(client);
		val response  = "Клиент успешно удален!";

		when(clientsRepo.findClientsByName(anyString())).thenReturn(Optional.of(client));

		val actual = clientsService.deleteClient(clientDto);

		verify(clientsRepo).delete(any(Clients.class));

		assertEquals(response, actual);
	}

	@Test
	void deleteClientThrownExceptionTest() {
		val clientDto = ClientsDto.builder()
								  .name("Test")
								  .build();

		when(clientsRepo.findClientsByName(anyString())).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> clientsService.deleteClient(clientDto));
	}

	@Test
	void getListOfAllClientsTest() {
		val client    = new Clients("Test");
		val clientDto = ConverterDto.toDtoFromClients(client);

		when(clientsRepo.findAll()).thenReturn(List.of(client));

		val actual = clientsService.getListOfAllClients();

		assertEquals(clientDto, actual.get(0));
	}

}