package com.ecommercejava.Ecommerce_Shopping_Backend.exceptions;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException(String message){
        super(message);
    }
}
