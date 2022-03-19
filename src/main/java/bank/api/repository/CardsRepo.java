package bank.api.repository;

import bank.api.entities.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepo extends JpaRepository<Cards, Long> {

    @Query("select max(number) from Cards")
    long getMaxCardsNumber();

    Cards findCardsByNumber(long number);
}
