package com.example.tinybank.service;

import com.example.tinybank.errors.AccountCreationException;
import com.example.tinybank.errors.AccountNotFoundException;
import com.example.tinybank.errors.PaymentException;
import com.example.tinybank.model.Account;

import java.util.List;

public interface AccountService {
    Account findById(Integer id) throws AccountNotFoundException;
    List<Account> findAll();
    List<Account> finAllByClientId(Integer id);
    void saveAccount(Account account) throws AccountCreationException;
    void deleteById(Integer id);
    void makePayment(Integer senderId,Integer recipientId,Long value) throws PaymentException, AccountCreationException, AccountNotFoundException;
}
