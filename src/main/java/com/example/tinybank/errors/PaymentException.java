package com.example.tinybank.errors;

public class PaymentException extends Exception{
    public PaymentException(String message){
        super(message);
    }
}
