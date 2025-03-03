package com.ecommercejava.Ecommerce_Shopping_Backend.service;

import com.ecommercejava.Ecommerce_Shopping_Backend.enums.UserType;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.ApplicationUser;
import com.ecommercejava.Ecommerce_Shopping_Backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUtil {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public ApplicationUser checkEmailExist(String email){
        ApplicationUser user = applicationUserRepository.findByEmail(email);
        return user;
    }

    public boolean isSeller(ApplicationUser user){
        if(user.getUserType().equals(UserType.SELLER)){
            return true;
        }
        return false;
    }
}
