package com.example.tinybank.service.impl;

import com.example.tinybank.errors.ClientCreationException;
import com.example.tinybank.model.Client;
import com.example.tinybank.repository.ClientJpaRepository;
import com.example.tinybank.service.ClientService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientServiceImpl implements ClientService {
    private final ClientJpaRepository clientJpaRepository;

    public ClientServiceImpl(ClientJpaRepository clientJpaRepository) {
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public Client findById(Integer id){
        return clientJpaRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find client by ID - "+id));
    }

    @Override
    public Client findByName(String name){
        return clientJpaRepository
                .findClientByName(name);
    }

    @Override
    public List<Client> findAll(){
        return clientJpaRepository
                .findAll();
    }

    @Override
    public void saveClient(Client client) throws ClientCreationException {
        var client1 = clientJpaRepository.findClientByUsername(client.getUsername());
        if (client1 != null && !client1.getId().equals(client.getId()))
            throw new ClientCreationException("A Client with the same username already exists");
        clientJpaRepository
                .save(client);
    }

    @Override
    public void deleteById(Integer id){
        clientJpaRepository.deleteById(id);
    }

    @Override
    public Client findClientByUsername(String username){
        return clientJpaRepository
                .findClientByUsername(username);
    }
}
