package com.example.tinybank.errors;

public class AccountCreationException extends Exception{
    public AccountCreationException(String message){
        super(message);
    }
}
