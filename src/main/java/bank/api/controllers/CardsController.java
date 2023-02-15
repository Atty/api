package bank.api.controllers;

import bank.api.dto.CardsDto;
import bank.api.services.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardsController {

	private final CardsService cardsService;
	private final HttpStatus   OK = HttpStatus.OK;

	@GetMapping("/{clientsName}")
	public ResponseEntity<List<CardsDto>> getAllCardsByClient(@PathVariable String clientsName) {
		return new ResponseEntity<>(cardsService.getAllCardsByClient(clientsName), OK);
	}

	@GetMapping("/checkBalance")
	public ResponseEntity<Integer> checkCardBalance(@RequestParam String cardNumber) {
		return new ResponseEntity<>(cardsService.checkBalance(cardNumber), OK);
	}

	@PostMapping("/add")
	public ResponseEntity<CardsDto> addNewCardToBankAccount(@RequestBody String accountNumber) {
		return new ResponseEntity<>(cardsService.addCardsToBankAccount(accountNumber), OK);
	}

	@PostMapping("/addFunds")
	public ResponseEntity<String> addFundsOnCard(@RequestBody CardsDto cardsDto) {
		return new ResponseEntity<>(cardsService.addFundsOnCard(cardsDto), OK);
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteCard(@RequestBody CardsDto cardsDto) {
		return new ResponseEntity<>(cardsService.removeCard(cardsDto), OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<CardsDto>> getAllCards() {
		return new ResponseEntity<>(cardsService.getListOfAllCards(), OK);
	}

}
