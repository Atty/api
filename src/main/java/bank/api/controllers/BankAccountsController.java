package bank.api.controllers;

import bank.api.dto.BankAccountsDto;
import bank.api.services.BankAccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bankAccounts")
@RequiredArgsConstructor
public class BankAccountsController {

    private final BankAccountsService bankAccountsService;
    private final HttpStatus          OK = HttpStatus.OK;

    @GetMapping("/getAll")
    public ResponseEntity<List<BankAccountsDto>> getListOfAllBankAccounts() {
        return new ResponseEntity<>(bankAccountsService.getListOfAllBankAccounts(), OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBankAccount(@RequestBody BankAccountsDto bankAccountsDto) {
        return new ResponseEntity<>(bankAccountsService.addBankAccount(bankAccountsDto), OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteBankAccount(@RequestBody BankAccountsDto bankAccountsDto) {
        return new ResponseEntity<>(bankAccountsService.deleteBankAccount(bankAccountsDto), OK);
    }

}
