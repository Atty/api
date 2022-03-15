package bank.api.services;


import bank.api.entities.Clients;

/**
 * For future extension
 */
public interface ClientsService {

    /**
     * @param clients
     */
    void addClient(Clients clients);

    /**
     * @param clients
     */
    void deleteClients(Clients clients);

}
