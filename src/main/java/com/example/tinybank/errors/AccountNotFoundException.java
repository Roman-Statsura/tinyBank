package com.example.tinybank.errors;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String message){
        super(message);
    }
}
