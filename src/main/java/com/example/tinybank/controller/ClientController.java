package com.example.tinybank.controller;

import com.example.tinybank.errors.ClientCreationException;
import com.example.tinybank.model.Client;
import com.example.tinybank.model.dto.ClientForm;
import com.example.tinybank.model.dto.CustomError;
import com.example.tinybank.service.impl.ClientServiceImpl;
import com.example.tinybank.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import java.util.List;

@Controller
public class ClientController {
    private final ClientServiceImpl clientServiceImpl;

    @Autowired
    public ClientController(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }

    @GetMapping("/clients")
    public String findAll(Model model){
        List<Client> clients = clientServiceImpl.findAll();
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
            var client = new Client(clientForm);
            clientServiceImpl.saveClient(client);
        }
        catch (ClientCreationException e){
            model.addAttribute("error",new CustomError(e.getMessage()));
            return Constants.CLIENT_CREATE_PAGE;
        }
        return Constants.REDIRECT_TO_CLIENTS_PAGE;
    }
    @GetMapping("client-delete/{id}")
    public String deleteClient(@PathVariable("id") Integer id){
        clientServiceImpl.deleteById(id);
        return Constants.REDIRECT_TO_CLIENTS_PAGE;
    }
    @GetMapping("client-update/{id}")
    public String updateClientForm(@PathVariable("id") Integer id,Model model){
        var client = clientServiceImpl.findById(id);
        model.addAttribute("client",client);
        return Constants.CLIENT_UPDATE_PAGE;
    }
    @PostMapping("/client-update")
    public String updateClient(@Valid Client client, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            return Constants.CLIENT_UPDATE_PAGE;
        }
        try {
            clientServiceImpl.saveClient(client);
        }
        catch (ClientCreationException e){
            model.addAttribute("error",new CustomError(e.getMessage()));
            return Constants.CLIENT_UPDATE_PAGE;
        }
        return Constants.REDIRECT_TO_CLIENTS_PAGE;
    }
}
