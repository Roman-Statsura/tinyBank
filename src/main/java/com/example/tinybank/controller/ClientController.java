package com.example.tinybank.controller;

import com.example.tinybank.errors.ClientCreationException;
import com.example.tinybank.model.Client;
import com.example.tinybank.service.ClientService;
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

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Controller
public class ClientController {
    private final ClientService clientService;


    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String findAll(Model model){
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients",clients);
        return "client-list";
    }
    @GetMapping("/client-create")
    public String createClientForm(ClientForm clientForm){
        return Constants.CLIENT_CREATE_PAGE;
    }
    @PostMapping("/client-create")
    public String createClient(@Valid ClientForm clientForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return Constants.CLIENT_CREATE_PAGE;
        }
        try {
            Client client = new Client(clientForm);
            client.setCorrectFields();
            clientService.saveClient(client);
        }
        catch (ClientCreationException e){
            model.addAttribute("error",new CustomError(e.getMessage()));
            return Constants.CLIENT_CREATE_PAGE;
        }
        return Constants.REDIRECT_TO_CLIENTS_PAGE;
    }
    @GetMapping("client-delete/{id}")
    public String deleteClient(@PathVariable("id") Integer id){
        clientService.deleteById(id);
        return Constants.REDIRECT_TO_CLIENTS_PAGE;
    }
    @GetMapping("client-update/{id}")
    public String updateClientForm(@PathVariable("id") Integer id,Model model){
        Client client = clientService.findById(id);
        model.addAttribute("client",client);
        return Constants.CLIENT_UPDATE_PAGE;
    }
    @PostMapping("/client-update")
    public String updateClient(@Valid Client client, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            return Constants.CLIENT_UPDATE_PAGE;
        }
        try {
            client.setCorrectFields();
            clientService.saveClient(client);
        }
        catch (ClientCreationException e){
            model.addAttribute("error",new CustomError(e.getMessage()));
            return Constants.CLIENT_UPDATE_PAGE;
        }
        return Constants.REDIRECT_TO_CLIENTS_PAGE;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class CustomError {
        private String message;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ClientForm {
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
}
