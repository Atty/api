package bank.api.repository;

import bank.api.entities.BankAccounts;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountsRepo extends CrudRepository<BankAccounts, Long> {

    BankAccounts findBankAccountsByNumber(String number);
}
