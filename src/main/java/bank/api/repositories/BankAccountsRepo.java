package bank.api.repositories;

import bank.api.entities.BankAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountsRepo extends JpaRepository<BankAccounts, Long> {

    @Query("select b " +
            "from BankAccounts b " +
            "where b.number = ?1")
    BankAccounts findBankAccountsByNumber(String number);
}