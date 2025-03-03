package com.ecommercejava.Ecommerce_Shopping_Backend.controller;

import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.WrongCredentialsException;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.ApplicationUser;
import com.ecommercejava.Ecommerce_Shopping_Backend.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class CommonUserController {

    @Autowired
    CommonUserService commonUserService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody ApplicationUser user){
        commonUserService.createUser(user);
        return new ResponseEntity("User Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/authenticate")
    public ResponseEntity authenticateUser(@RequestHeader String Authorization){
        String details[] = Authorization.split(":");
        String email = details[0];
        String password = details[1];

        try{
            commonUserService.authenticateUser(email, password);
            return new ResponseEntity("User login successfully", HttpStatus.OK);
        }catch (WrongCredentialsException wrongCredentialsException){
            return new ResponseEntity(wrongCredentialsException.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


}
