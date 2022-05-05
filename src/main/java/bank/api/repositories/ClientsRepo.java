package bank.api.repositories;

import bank.api.entities.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepo extends JpaRepository<Clients, Long> {

    @Query("select c " +
            "from Clients c " +
            "where c.name = ?1")
    Clients findClientsByName(String name);
}