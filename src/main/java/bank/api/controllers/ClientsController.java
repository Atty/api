package bank.api.controllers;

import bank.api.dto.CardsDto;
import bank.api.dto.CompositeDto;
import bank.api.services.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientsController {

    private final CardsService cardsService;

    @GetMapping("{clientsName}")
    public ResponseEntity<List<CardsDto>> getAllCards(@PathVariable String clientsName) {
        List<CardsDto> cardsDtoList = cardsService.getCardsByClient(clientsName);
        return new ResponseEntity<>(cardsDtoList, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Integer> checkAccountBalance(@RequestParam long cardNumber) {
        return new ResponseEntity<>(cardsService.checkBalance(cardNumber), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CardsDto> addNewCard(@RequestBody CompositeDto compositeDto) {
        CardsDto cardsDto = cardsService.addCards(compositeDto.getAccountNumber());
        return new ResponseEntity<>(cardsDto, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<String> addFundsOnCard(@RequestBody CompositeDto compositeDto) {
        cardsService.addFundsByCard(compositeDto.getCardNumber(), compositeDto.getAmountOfMoney());
        return new ResponseEntity<>("Счет успешно пополнен!", HttpStatus.OK);
    }
}
