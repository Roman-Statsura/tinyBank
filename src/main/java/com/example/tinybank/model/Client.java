package com.example.tinybank.model;

import com.example.tinybank.model.dto.ClientForm;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "client")
@Getter

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters" )
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    @NotNull
    @Size(min=5,message = "Password must not be shorter then 5 characters")
    private String password;
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Enter date")
    private Date birthDate;
    @Column(name = "name")
    @Pattern(regexp = "^[a-zA-z]{2,30}$",message = "Enter the name in Latin in one word in the range of 2 - 30 characters")
    private String name;
    @Pattern(regexp = "^[a-zA-z]{2,30}$",message = "Enter the surname in Latin in one word in the range of 2 - 30 characters")
    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Account> accounts;

    public Client(ClientForm clientForm){
        setName(clientForm.getName());
        setSurname(clientForm.getSurname());
        setUsername(clientForm.getUsername());
        setPassword(clientForm.getPassword());
        setBirthDate(clientForm.getBirthDate());
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setName(String name) {
        String newName = name.trim().toLowerCase(Locale.ROOT);
        this.name = newName.substring(0, 1).toUpperCase() + newName.substring(1);
    }

    public void setSurname(String surname) {
        String newSurname = surname.trim().toLowerCase(Locale.ROOT);
        this.surname = newSurname.substring(0, 1).toUpperCase() + newSurname.substring(1);
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}

