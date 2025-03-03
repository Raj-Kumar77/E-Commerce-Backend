package com.ecommercejava.Ecommerce_Shopping_Backend.service;

import com.ecommercejava.Ecommerce_Shopping_Backend.model.ApplicationUser;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.Product;
import com.ecommercejava.Ecommerce_Shopping_Backend.requestBody.CreateProductDTO;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public Product productMapper(CreateProductDTO productDTO, ApplicationUser seller){
        Product product = new Product();
        product.setProductCategory(productDTO.getProductCategory());
        product.setName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setSeller(seller);
        product.setRating(0.0);
        product.setQuantitySold(0);

        return product;
    }

}
