package com.example.tinybank;

import com.example.tinybank.model.Client;
import com.example.tinybank.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JpaClientRepositoryTest {
    @Autowired
    private ClientService clientService;
    @Test
    public void findByIdTest(){
        Client client = clientService.findById(1);
        var x = client.getAccounts().stream().findFirst().get().getBalance();
        assertEquals("Roman",client.getName());
    }
    @Test
    public void findByNameTest(){
        Client client = clientService.findByName("Roman");
        assertEquals(1,client.getId());
    }
}