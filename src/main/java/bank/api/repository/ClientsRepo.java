package bank.api.repository;

import bank.api.entities.Clients;
import org.springframework.data.repository.CrudRepository;

public interface ClientsRepo extends CrudRepository<Clients, Long> {

    Clients findClientsByName(String name);
}
