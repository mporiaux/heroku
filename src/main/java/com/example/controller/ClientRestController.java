/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;
import com.example.domain.Client;
import com.example.domain.ClientService;
import java.util.List;
import javax.servlet.ServletContext;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
  

  
@RestController
public class ClientRestController {


@Autowired
private ServletContext context;

 
       
    @Autowired
    ClientService clientService;  //Service which will do all data retrieval/manipulation work
  
      
    //-------------------Retrieve All Clients--------------------------------------------------------
      
  @RequestMapping(value = "/Client/", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listAllClients() {
        List<Client> clients = clientService.findAllClients();
        if(clients.isEmpty()){
            return new ResponseEntity<List<Client>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }
  
  
    //-------------------Retrieve Single Client--------------------------------------------------------
      
    @RequestMapping(value = "/Client/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Client> getClient(@PathVariable("id") long id) {
        System.out.println("Fetching Client with id " + id);
        Client Client = clientService.findById(id);
        if (Client == null) {
            System.out.println("Client with id " + id + " not found");
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(Client, HttpStatus.OK);
    }
  
      
      
    //-------------------Create a Client--------------------------------------------------------
      
    @RequestMapping(value = "/Client/", method = RequestMethod.POST)
    public ResponseEntity<Void> createClient(@RequestBody Client Client, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Client " + Client.getNom()+" "+Client.getPrenom());
  
        if (clientService.isClientExist(Client)) {
            System.out.println("A Client with name " + Client.getNom() + "  "+Client.getPrenom()+" already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
  
        clientService.saveClient(Client);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Client/{id}").buildAndExpand(Client.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
  
      
    //------------------- Update a Client --------------------------------------------------------
      
    @RequestMapping(value = "/Client/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client Client) {
        System.out.println("Updating Client " + id);
          
        Client currentClient = clientService.findById(id);
          
        if (currentClient==null) {
            System.out.println("Client with id " + id + " not found");
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
  
        currentClient.setNom(Client.getNom());
        currentClient.setPrenom(Client.getPrenom());
        currentClient.setId(Client.getId());
          
        clientService.updateClient(currentClient);
        return new ResponseEntity<Client>(currentClient, HttpStatus.OK);
    }
  
    //------------------- Delete a Client --------------------------------------------------------
      
    @RequestMapping(value = "/Client/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Client> deleteClient(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Client with id " + id);
  
        Client Client = clientService.findById(id);
        if (Client == null) {
            System.out.println("Unable to delete. Client with id " + id + " not found");
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
  
        clientService.deleteClientById(id);
        return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
    }
  
      
    //------------------- Delete All Clients --------------------------------------------------------
      
    @RequestMapping(value = "/Client/", method = RequestMethod.DELETE)
    public ResponseEntity<Client> deleteAllClients() {
        System.out.println("Deleting All Clients");
  
        clientService.deleteAllClients();
        return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
    }
    
    @Bean 
    ClientService clientService(){
        return new ClientService();
    }
        
}
