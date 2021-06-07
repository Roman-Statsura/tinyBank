package com.example.tinybank.model;

import com.example.tinybank.controller.AccountController;
import com.example.tinybank.utils.AccountStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "balance")
    @NotNull(message = "enter balance")
    @Min(value = 0, message = "balance cannot be less than zero")
    private Double balance;
    @Column(name = "open_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Enter date")
    private Date openDate;
    @Column(name = "close_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Enter date")
    private Date closeDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    public Account(AccountController.AccountForm accountForm) {
        setBalance(accountForm.getBalance());
        setOpenDate(accountForm.getOpenDate());
        setCloseDate(accountForm.getCloseDate());
        setStatus(accountForm.getStatus());
    }
}

