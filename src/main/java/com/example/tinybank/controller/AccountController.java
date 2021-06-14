package com.example.tinybank.controller;

import com.example.tinybank.errors.AccountCreationException;
import com.example.tinybank.errors.AccountNotFoundException;
import com.example.tinybank.errors.PaymentException;
import com.example.tinybank.model.Account;
import com.example.tinybank.model.Audit;
import com.example.tinybank.model.dto.AccountForm;
import com.example.tinybank.model.dto.CustomError;
import com.example.tinybank.model.dto.Payment;
import com.example.tinybank.service.impl.AccountServiceImpl;
import com.example.tinybank.service.impl.AuditServiceImpl;
import com.example.tinybank.service.impl.ClientServiceImpl;
import com.example.tinybank.utils.AuditAction;
import com.example.tinybank.utils.Constants;
import com.example.tinybank.utils.ObjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class AccountController {
    private final AccountServiceImpl accountServiceImpl;
    private final ClientServiceImpl clientServiceImpl;
    private final AuditServiceImpl auditServiceImpl;

    @Autowired
    public AccountController(AccountServiceImpl accountServiceImpl, ClientServiceImpl clientServiceImpl, AuditServiceImpl auditServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
        this.clientServiceImpl = clientServiceImpl;
        this.auditServiceImpl = auditServiceImpl;
    }
    @GetMapping("account-list/{id}")
    public String findAllAccountByClient(@PathVariable("id") Integer id,Model model){
        List<Account> accounts = accountServiceImpl.finAllByClientId(id);
        model.addAttribute("accounts", accounts);
        model.addAttribute("clId",id);
        return "account-list";
    }
    @GetMapping("/account-delete/{id}")
    public String deleteAccount(@PathVariable("id") Integer id){
        accountServiceImpl.deleteById(id);
        var audit = auditServiceImpl.createAudit(id,ObjectType.ACCOUNT,new Date(),
                AuditAction.DELETE,0d);
        auditServiceImpl.saveAudit(audit);
        return Constants.REDIRECT_TO_CLIENTS_PAGE;
    }

    @GetMapping("/account-create/{id}")
    public String createAccountForm(@PathVariable("id") Integer id, Model model,AccountForm accountForm){
        model.addAttribute("clId",id);
        return Constants.ACCOUNT_CREATE_PAGE;
    }
    @PostMapping("/account-create/{id}")
    public String createAccount(@PathVariable("id")Integer id, @Valid AccountForm accountForm,
                                BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()){
            return createAccountForm(id,model,accountForm);
        }
        try {
            var client = clientServiceImpl.findById(id);
            var account = new Account(accountForm);
            account.setClient(client);
            accountServiceImpl.saveAccount(account);

            var audit = auditServiceImpl.createAudit(account.getId(),ObjectType.ACCOUNT,new Date(),
                    AuditAction.CREATE,account.getBalance());
            auditServiceImpl.saveAudit(audit);
        }
        catch (AccountCreationException e){
            model.addAttribute("error",new CustomError(e.getMessage()));
            return createAccountForm(id,model,accountForm);
        }
        return "redirect:/account-list/{id}";
    }
    @GetMapping("/account-update/{id}")
    public String updateAccountForm(@PathVariable("id") Integer id,Model model) throws AccountNotFoundException {
        var account = accountServiceImpl.findById(id);
        model.addAttribute("accountForm",new AccountForm(account));
        return Constants.ACCOUNT_UPDATE_PAGE;
    }
    @PostMapping("/account-update")
    public String updateAccount(@Valid AccountForm accountForm,
                                BindingResult bindingResult, Model model) throws AccountNotFoundException {
        var account = accountServiceImpl.findById(accountForm.getId());
        if (bindingResult.hasErrors()){
            model.addAttribute("accountForm",accountForm);
            return Constants.ACCOUNT_UPDATE_PAGE;
        }
        try {
            Audit audit = null;
            if (!account.getBalance().equals(accountForm.getBalance())){
                account.setBalance(accountForm.getBalance());
                audit = auditServiceImpl.createAudit(account.getId(),ObjectType.ACCOUNT,new Date(),
                        AuditAction.UPDATE,account.getBalance());
            }
            account.setOpenDate(accountForm.getOpenDate());
            account.setCloseDate(accountForm.getCloseDate());
            account.setStatus(accountForm.getStatus());

            accountServiceImpl.saveAccount(account);
            auditServiceImpl.saveAudit(audit);
        }
        catch (AccountCreationException e){
            model.addAttribute("accountForm",accountForm);
            model.addAttribute("error",new CustomError(e.getMessage()));
            return Constants.ACCOUNT_UPDATE_PAGE;
        }
        return String.format("redirect:/account-list/%d",account.getClient().getId());
    }
    @GetMapping("/account-payment/{id}")
    public String openPaymentForm(@PathVariable("id") Integer id, Payment payment, Model model){
        model.addAttribute("id",id);
        return Constants.ACCOUNT_PAYMENT_PAGE;
    }
    @PostMapping("/account-payment/{id}")
    public String makePayment(@PathVariable("id") Integer id,Payment payment,Model model,
                              BindingResult bindingResult) throws AccountCreationException {
        if (bindingResult.hasErrors()){
            return openPaymentForm(id,payment,model);
        }
        try {
            accountServiceImpl.makePayment(id, payment.getTargetId(), payment.getValue());
        }
        catch (PaymentException e){
            model.addAttribute("paymentError",new CustomError(e.getMessage()));
            return Constants.ACCOUNT_PAYMENT_PAGE;
        }
        catch (AccountNotFoundException e){
            model.addAttribute("accountIdError",new CustomError(e.getMessage()));
            return Constants.ACCOUNT_PAYMENT_PAGE;
        }
        return Constants.REDIRECT_TO_CLIENTS_PAGE;
    }
}
