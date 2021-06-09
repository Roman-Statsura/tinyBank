package com.example.tinybank;

import com.example.tinybank.model.Client;
import com.example.tinybank.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JpaClientRepositoryTest {
    @Autowired
    private ClientServiceImpl clientServiceImpl;
    @Test
    public void findByIdTest(){
        Client client = clientServiceImpl.findById(1);
        var x = client.getAccounts().stream().findFirst().get().getBalance();
        assertEquals("Roman",client.getName());
    }
    @Test
    public void findByNameTest(){
        Client client = clientServiceImpl.findByName("Roman");
        assertEquals(1,client.getId());
    }
}