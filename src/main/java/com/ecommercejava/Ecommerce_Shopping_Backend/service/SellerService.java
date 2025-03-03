package com.ecommercejava.Ecommerce_Shopping_Backend.service;

import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.UserNotAllowed;
import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.UserNotFound;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.ApplicationUser;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.Product;
import com.ecommercejava.Ecommerce_Shopping_Backend.repository.ProductRepository;
import com.ecommercejava.Ecommerce_Shopping_Backend.requestBody.CreateProductDTO;
import com.ecommercejava.Ecommerce_Shopping_Backend.requestBody.MultiProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    UserUtil userUtil;
    @Autowired
    Mapper mapper;
    @Autowired
    ProductRepository productRepository;

    public void createProduct(CreateProductDTO createProductDTO, String sellerEmail){
        ApplicationUser user = userUtil.checkEmailExist(sellerEmail);
        if(user == null){
            throw new UserNotFound(String.format("Seller with email %s does not exist in system", sellerEmail));
        }
        boolean isSeller = userUtil.isSeller(user);

        if(isSeller == false){
            throw new UserNotAllowed(String.format("User with email %s is not allowed to add product", sellerEmail));
        }

        Product product = mapper.productMapper(createProductDTO, user);

        productRepository.save(product);
    }

    public void createMultiProduct(MultiProductDTO multiProductDTO, String sellerEmail){
        ApplicationUser user = userUtil.checkEmailExist(sellerEmail);
        if(user == null){
            throw new UserNotFound(String.format("Seller with email %s does not exist in system", sellerEmail));
        }
        boolean isSeller = userUtil.isSeller(user);

        if(isSeller == false){
            throw new UserNotAllowed(String.format("User with email %s is not allowed to add product", sellerEmail));
        }

        List<CreateProductDTO> productDTOs = multiProductDTO.getProducts();

        for(CreateProductDTO productDTO : productDTOs){
            Product product = mapper.productMapper(productDTO, user);
            productRepository.save(product);
        }
    }
}
