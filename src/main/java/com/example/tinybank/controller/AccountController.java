package com.example.tinybank.controller;

import com.example.tinybank.errors.AccountCreationException;
import com.example.tinybank.errors.AccountNotFoundException;
import com.example.tinybank.errors.PaymentException;
import com.example.tinybank.model.Account;
import com.example.tinybank.service.AccountService;
import com.example.tinybank.service.ClientService;
import com.example.tinybank.utils.AccountStatus;
import com.example.tinybank.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Controller
public class AccountController {
    private final AccountService accountService;
    private final ClientService clientService;

    @Autowired
    public AccountController(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }
    @GetMapping("account-list/{id}")
    public String findAllAccountByClient(@PathVariable("id") Integer id,Model model){
        List<Account> accounts = accountService.finAllByClientId(id);
        model.addAttribute("accounts", accounts);
        model.addAttribute("clId",id);
        return "account-list";
    }
    @GetMapping("/account-delete/{id}")
    public String deleteAccount(@PathVariable("id") Integer id){
        accountService.deleteById(id);
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
            var client = clientService.findById(id);
            Account account = new Account(accountForm);
            account.setClient(client);
            accountService.saveAccount(account);
        }
        catch (AccountCreationException e){
            model.addAttribute("error",new CustomError(e.getMessage()));
            return createAccountForm(id,model,accountForm);
        }
        return "redirect:/account-list/{id}";
    }
    @GetMapping("/account-update/{id}")
    public String updateAccountForm(@PathVariable("id") Integer id,Model model) throws AccountNotFoundException {
        Account account = accountService.findById(id);
        model.addAttribute("account",account);
        return Constants.ACCOUNT_UPDATE_PAGE;
    }
    @PostMapping("/account-update/{id}")
    public String updateAccount(@PathVariable("id") Integer id,@Valid Account account,
                                BindingResult bindingResult, Model model){
        account.setClient(clientService.findById(id));
        if (bindingResult.hasErrors()){
            model.addAttribute("account",account);
            return Constants.ACCOUNT_UPDATE_PAGE;
        }
        account.setClient(clientService.findById(id));
        try {
            accountService.saveAccount(account);
        }
        catch (AccountCreationException e){
            model.addAttribute("account",account);
            model.addAttribute("error",new CustomError(e.getMessage()));
            return Constants.ACCOUNT_UPDATE_PAGE;
        }
        return "redirect:/account-list/{id}";
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
            accountService.makePayment(id, payment.getTargetId(), payment.getValue());
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Payment{
        @NotNull(message = "enter id")
        private Integer targetId;
        @NotNull(message = "enter value")
        private Long value;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class AccountForm {
        @NotNull(message = "enter balance")
        @Min(value = 0, message = "balance cannot be less than zero")
        private Double balance;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Temporal(TemporalType.DATE)
        @NotNull(message = "Enter date")
        private Date openDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Temporal(TemporalType.DATE)
        @NotNull(message = "Enter date")
        private Date closeDate;
        @Enumerated(EnumType.STRING)
        private AccountStatus status;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class CustomError {
        private String message;
    }
}
