package com.ecommercejava.Ecommerce_Shopping_Backend.controller;

import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.UserNotAllowed;
import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.UserNotFound;
import com.ecommercejava.Ecommerce_Shopping_Backend.requestBody.CreateProductDTO;
import com.ecommercejava.Ecommerce_Shopping_Backend.requestBody.MultiProductDTO;
import com.ecommercejava.Ecommerce_Shopping_Backend.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestBody CreateProductDTO createProductDTO, @RequestParam String sellerEmail){
        try{

            sellerService.createProduct(createProductDTO, sellerEmail);
            return new ResponseEntity("Product added successfully", HttpStatus.CREATED);

        }catch(UserNotFound userNotFound){
            return new ResponseEntity(userNotFound.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UserNotAllowed userNotAllowed){
            return new ResponseEntity(userNotAllowed.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/multi/create")
    public ResponseEntity createMultiProducts(@RequestBody MultiProductDTO multiProductDTO, @RequestParam String sellerEmail){
        try{
            sellerService.createMultiProduct(multiProductDTO, sellerEmail);
            return new ResponseEntity("Products added successfully", HttpStatus.CREATED);

        }catch(UserNotFound userNotFound){
            return new ResponseEntity(userNotFound.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UserNotAllowed userNotAllowed){
            return new ResponseEntity(userNotAllowed.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
