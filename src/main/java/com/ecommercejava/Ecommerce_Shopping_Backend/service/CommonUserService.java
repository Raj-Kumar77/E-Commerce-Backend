package com.ecommercejava.Ecommerce_Shopping_Backend.service;

import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.WrongCredentialsException;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.ApplicationUser;
import com.ecommercejava.Ecommerce_Shopping_Backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonUserService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    UserUtil userUtil;

    public void createUser(ApplicationUser user){
        applicationUserRepository.save(user);
    }

    public boolean authenticateUser(String email, String password){
        ApplicationUser user = userUtil.checkEmailExist(email);
        if(user == null){
            throw new WrongCredentialsException(String.format("User entered wrong email %s",email));
        }

        String actualPassword = user.getPassword();
        if(actualPassword.equals(password)){
            return true;
        }else {
            throw new WrongCredentialsException(String.format("Wrong password entered %s", password));
        }
    }
}
