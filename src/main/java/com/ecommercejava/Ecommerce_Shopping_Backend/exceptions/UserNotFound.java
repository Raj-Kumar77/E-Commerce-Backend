package com.ecommercejava.Ecommerce_Shopping_Backend.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message){
        super(message);
    }
}
