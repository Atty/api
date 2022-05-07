package bank.api.controllers;

import bank.api.dto.CardsDto;
import bank.api.services.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientsController {

    private final CardsService cardsService;

    @GetMapping("/{clientsName}")
    public ResponseEntity<List<CardsDto>> getAllCards(@PathVariable String clientsName) {
        List<CardsDto> cardsDtoList = cardsService.getCardsByClient(clientsName);
        return new ResponseEntity<>(cardsDtoList, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Integer> checkAccountBalance(@RequestParam String cardNumber) {
        return new ResponseEntity<>(cardsService.checkBalance(cardNumber), HttpStatus.OK);
    }

    @PostMapping("/addCard")
    public ResponseEntity<CardsDto> addNewCard(@RequestBody String accountNumber) {
        CardsDto cardsDto = cardsService.addCards(accountNumber);
        return new ResponseEntity<>(cardsDto, HttpStatus.OK);
    }

    @PostMapping("/addFunds")
    public ResponseEntity<String> addFundsOnCard(@RequestBody CardsDto cardsDto) {
        cardsService.addFundsByCard(cardsDto.getNumber(), cardsDto.getFunds());
        return new ResponseEntity<>("Счет успешно пополнен!", HttpStatus.OK);
    }
}
