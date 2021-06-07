package com.example.tinybank.service;

import com.example.tinybank.errors.AccountCreationException;
import com.example.tinybank.errors.AccountNotFoundException;
import com.example.tinybank.errors.PaymentException;
import com.example.tinybank.model.Account;
import com.example.tinybank.repository.AccountJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountService {
    private final AccountJpaRepository accountJpaRepository;

    public AccountService(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
    }
    public Account findById(Integer id) throws AccountNotFoundException {
        return accountJpaRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Cannot find account by ID - "+id));
    }
    public List<Account> findAll(){
        return accountJpaRepository
                .findAll();
    }
    public List<Account> finAllByClientId(Integer id){
        return accountJpaRepository
                .findAccountsByClient_Id(id);
    }
    public void saveAccount(Account account) throws AccountCreationException {
        if (account.getCloseDate().before(account.getOpenDate()))
            throw new AccountCreationException("The closing date cannot be earlier then the opening date");
        accountJpaRepository
                .save(account);
    }
    public void deleteById(Integer id){
        accountJpaRepository.deleteAccountCustom(id);
    }

    public void makePayment(Integer senderId,Integer recipientId,Long value) throws PaymentException, AccountCreationException, AccountNotFoundException {
        Account sender = findById(senderId);
        Account recipient = findById(recipientId);
        if (senderId.equals(recipientId)){
            throw new PaymentException("Sender and Recipient are equal");
        }
        if ((sender.getBalance() - value)<0){
            throw new PaymentException("Sender doesn't have enough funds");
        }
        sender.setBalance(sender.getBalance() - value);
        recipient.setBalance(recipient.getBalance() + value);
        saveAccount(sender);
        saveAccount(recipient);
    }
}
