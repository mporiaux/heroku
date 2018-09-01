package com.example.domain;

import java.util.List;

public interface ClientServiceInterface {

    void deleteAllClients();

    void deleteClientById(long id);

    List<Client> findAllClients();

    Client findById(long id);

    boolean isClientExist(Client client);

    void saveClient(Client client);

    void updateClient(Client currentClient);
    
}
