package com.ecommercejava.Ecommerce_Shopping_Backend.service;

import com.ecommercejava.Ecommerce_Shopping_Backend.model.Product;
import com.ecommercejava.Ecommerce_Shopping_Backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductUtil {

    @Autowired
    ProductRepository productRepository;

    public Product isValidProductID(UUID id){
        Product product = productRepository.findById(id).orElse(null);
        return product;
    }
}
