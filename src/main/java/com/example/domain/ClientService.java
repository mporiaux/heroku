/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Michel
 */
public class ClientService {

    private List<Client> lc = new ArrayList<>();

    public List<Client> findAllClients() {
        return lc;
    }

    public void saveClient(Client client) {
        lc.add(client);
    }

    public void updateClient(Client currentClient) {
        Client ctrouve = null;
        for (Client c : lc) {
            if (c.getId() == currentClient.getId()) {
                break;
            }
        }
        if (ctrouve != null) {
            ctrouve.setNom(currentClient.getNom());
            ctrouve.setPrenom(currentClient.getPrenom());
        }
    }

    public Client findById(long id) {
        Client ctrouve = null;
        for (Client c : lc) {
            if (c.getId() == id) {
                break;
            }
        }
        return ctrouve;
    }

    public boolean isClientExist(Client client) {

        for (Client c : lc) {
            if (c.getNom().equals(client.getNom()) && client.getPrenom().equals(client.getPrenom())) {
                return true;
            }
        }
        return false;
    }

    public void deleteClientById(long id) {
        Iterator<Client> itc = lc.iterator();
        while (itc.hasNext()) {
             Client c = itc.next();
             if(c.getId()==id) {
                 itc.remove();
                 break;
             }
        }
    }
    

    public void deleteAllClients() {
        lc.clear();
    }

}
