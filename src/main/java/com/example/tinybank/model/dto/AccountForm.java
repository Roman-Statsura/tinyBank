package com.example.tinybank.model.dto;

import com.example.tinybank.model.Account;
import com.example.tinybank.utils.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountForm {
    private Integer id;
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

    public AccountForm(Account account){
        setId(account.getId());
        setBalance(account.getBalance());
        setOpenDate(account.getOpenDate());
        setCloseDate(account.getCloseDate());
        setStatus(account.getStatus());
    }
}
