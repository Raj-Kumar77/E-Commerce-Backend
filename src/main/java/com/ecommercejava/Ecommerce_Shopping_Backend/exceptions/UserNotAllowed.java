package com.ecommercejava.Ecommerce_Shopping_Backend.exceptions;

public class UserNotAllowed extends RuntimeException{
    public UserNotAllowed(String message){
        super(message);
    }
}
