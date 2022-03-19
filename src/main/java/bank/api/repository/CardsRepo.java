package bank.api.repository;

import bank.api.entities.Cards;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CardsRepo extends CrudRepository<Cards, Long> {

    @Query("select max(number) from Cards")
    long getMaxCardsNumber();

    Cards findCardsByNumber(long number);
}
