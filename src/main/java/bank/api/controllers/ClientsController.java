package bank.api.controllers;

import bank.api.dto.ClientsDto;
import bank.api.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientsController {

    private final ClientsService clientsService;
    private final HttpStatus     OK = HttpStatus.OK;

    @GetMapping("/getAll")
    public ResponseEntity<List<ClientsDto>> getListOfAllClients() {
        return new ResponseEntity<>(clientsService.getListOfAllClients(), OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewClient(@RequestBody ClientsDto clientsDto) {
        return new ResponseEntity<>(clientsService.addClient(clientsDto), OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteClient(@RequestBody ClientsDto clientsDto) {
        return new ResponseEntity<>(clientsService.deleteClient(clientsDto), OK);
    }
}
