package com.example.tinybank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientForm {
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters" )
    private String username;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}$",
            message = "The password must contain letters,num,upper letter,special char and is in the range from 8 to 30 characters")
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Enter date")
    private Date birthDate;
    @Pattern(regexp = "^[a-zA-z]{2,30}$",message = "Enter the name in Latin in one word in the range of 2 - 30 characters")
    private String name;
    @Pattern(regexp = "^[a-zA-z]{2,30}$",message = "Enter the surname in Latin in one word in the range of 2 - 30 characters")
    private String surname;
}