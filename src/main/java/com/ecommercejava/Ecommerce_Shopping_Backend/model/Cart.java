package com.ecommercejava.Ecommerce_Shopping_Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @OneToOne
    ApplicationUser shopper;
    int totalAmount;
    int totalQuantity;
    @OneToMany
    List<Product> products;
}

//find those users they cart have more than 5 products their quantitiy should be greater then 10;
//@Query(value = "select id from cart where COUNT(products) > 5 HAVING totalQuantity > 10", nativeQuery = true)
//List<ApplicationUser> findUsers();

