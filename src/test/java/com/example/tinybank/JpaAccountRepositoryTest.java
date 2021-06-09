package com.example.tinybank;

import com.example.tinybank.model.Account;
import com.example.tinybank.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JpaAccountRepositoryTest {
    @Autowired
    private AccountServiceImpl accountServiceImpl;

//    @Test
//    public void findByIdTest(){
//        Account account = accountService.findById(2);
//        assertEquals(551000.00,account.getBalance());
//    }
    @Test
    public void findAllTest(){
        List<Account> accounts = accountServiceImpl.findAll();
        assertEquals(3,accounts.size());
    }
    @Test
    public void finAllByClientIdTest(){
        var list = accountServiceImpl.finAllByClientId(1);
        int x = 7;
    }
}
