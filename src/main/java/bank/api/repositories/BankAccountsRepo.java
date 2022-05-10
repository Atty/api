package bank.api.repositories;

import bank.api.entities.BankAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountsRepo extends JpaRepository<BankAccounts, Long> {

    @Query("select b " +
            "from BankAccounts b " +
            "where b.number = ?1")
    Optional<BankAccounts> findBankAccountsByNumber(String number);
}