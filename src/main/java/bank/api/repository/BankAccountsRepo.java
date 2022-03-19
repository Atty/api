package bank.api.repository;

import bank.api.entities.BankAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountsRepo extends JpaRepository<BankAccounts, Long> {

    BankAccounts findBankAccountsByNumber(String number);
}
