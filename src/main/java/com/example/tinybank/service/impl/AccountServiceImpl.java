package com.example.tinybank.service.impl;

import com.example.tinybank.errors.AccountCreationException;
import com.example.tinybank.errors.AccountNotFoundException;
import com.example.tinybank.errors.PaymentException;
import com.example.tinybank.model.Account;
import com.example.tinybank.model.Audit;
import com.example.tinybank.repository.AccountJpaRepository;
import com.example.tinybank.service.AccountService;
import com.example.tinybank.utils.AuditAction;
import com.example.tinybank.utils.ObjectType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AccountServiceImpl implements AccountService {
    private final AccountJpaRepository accountJpaRepository;
    private final AuditServiceImpl auditService;

    public AccountServiceImpl(AccountJpaRepository accountJpaRepository, AuditServiceImpl auditService) {
        this.accountJpaRepository = accountJpaRepository;
        this.auditService = auditService;
    }

    @Override
    public Account findById(Integer id) throws AccountNotFoundException {
        return accountJpaRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Cannot find account by ID - "+id));
    }

    @Override
    public List<Account> findAll(){
        return accountJpaRepository
                .findAll();
    }

    @Override
    public List<Account> finAllByClientId(Integer id){
        return accountJpaRepository
                .findAccountsByClient_Id(id);
    }

    @Override
    public void saveAccount(Account account) throws AccountCreationException {
        if (account.getCloseDate().before(account.getOpenDate()))
            throw new AccountCreationException("The closing date cannot be earlier then the opening date");
        accountJpaRepository
                .save(account);
    }

    @Override
    public void deleteById(Integer id){
        accountJpaRepository.deleteById(id);
    }

    @Override
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
        Audit audit1 = auditService.createAudit(senderId,ObjectType.ACCOUNT,new Date(),
                AuditAction.UPDATE,sender.getBalance());
        Audit audit2 = auditService.createAudit(recipientId,ObjectType.ACCOUNT,new Date(),
                AuditAction.UPDATE,recipient.getBalance());
        auditService.saveAudit(audit1);
        auditService.saveAudit(audit2);
    }
}
