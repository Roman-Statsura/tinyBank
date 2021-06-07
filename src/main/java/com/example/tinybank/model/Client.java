package com.example.tinybank.model;

import com.example.tinybank.controller.ClientController;
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
@Setter
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

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts;

    public Client(ClientController.ClientForm clientForm){
        setName(clientForm.getName());
        setSurname(clientForm.getSurname());
        setUsername(clientForm.getUsername());
        setPassword(clientForm.getPassword());
        setBirthDate(clientForm.getBirthDate());
    }
    public void setCorrectFields(){
        if (name!=null && surname!=null && username!=null) {
            String newName = getName().trim().toLowerCase(Locale.ROOT);
            setName(newName.substring(0, 1).toUpperCase() + newName.substring(1));

            String newSurname = getSurname().trim().toLowerCase(Locale.ROOT);
            setSurname(newSurname.substring(0, 1).toUpperCase() + newSurname.substring(1));

            setUsername(getUsername().trim());
        }
    }
}

