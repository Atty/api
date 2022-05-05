package bank.api.services;


import bank.api.dto.ClientsDto;
import bank.api.entities.Clients;

import java.util.List;

public interface ClientsService {

    /**
     * Данный метод позволяет добавить нового клиента
     * в базу данных
     *
     * @param clients клиент, которого необзодимо добавить
     */
    void addClient(Clients clients);

    /**
     * Данный метод позволяет удалить
     * существующего клиента из базы данных
     *
     * @param clients клиент, которого необходимо удалить
     */
    void deleteClients(Clients clients);

    /**
     * Данный метод позволяет получить список всех клиентов
     *
     * @return список всех клиентов
     */
    List<ClientsDto> getListOfAllClients();

}
