package com.example.tinybank.service;

import com.example.tinybank.errors.ClientCreationException;
import com.example.tinybank.model.Client;

import java.util.List;

public interface ClientService {
    Client findById(Integer id);
    Client findByName(String name);
    List<Client> findAll();
    void saveClient(Client client) throws ClientCreationException;
    void deleteById(Integer id);
    Client findClientByUsername(String username);
}
