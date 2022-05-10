package bank.api.repositories;

import bank.api.entities.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardsRepo extends JpaRepository<Cards, Long> {

    @Query("select max(number) " +
            "from Cards")
    long getMaxCardsNumber();

    @Query("select c " +
            "from Cards c " +
            "where c.number = ?1")
    Optional<Cards> findCardsByNumber(String number);
}