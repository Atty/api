package bank.api.services;


import bank.api.dto.ClientsDto;

import java.util.List;

public interface ClientsService {

    /**
     * Данный метод позволяет добавить нового клиента в базу данных
     *
     * @param clientsDto клиент, которого необходимо добавить (нужно указать только имя)
     * @return сообщение о выполнении операции
     */
    String addClient(ClientsDto clientsDto);

    /**
     * Данный метод позволяет удалить существующего клиента из базы данных
     *
     * @param clientsDto клиент, которого необходимо удалить (нужно указать только имя)
     * @return сообщение о выполнении операции
     */
    String deleteClient(ClientsDto clientsDto);

    /**
     * Данный метод позволяет получить список всех клиентов
     *
     * @return список всех клиентов
     */
    List<ClientsDto> getListOfAllClients();

}
