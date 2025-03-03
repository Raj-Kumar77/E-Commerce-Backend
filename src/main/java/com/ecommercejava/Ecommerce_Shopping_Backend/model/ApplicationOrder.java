package com.ecommercejava.Ecommerce_Shopping_Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApplicationOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    ApplicationUser shopper;
    Date orderPlaced;
    Date expectedDelivery;
    String status;
    int totalAmount;
    String paymentMethod;
    int totalQuantity;
    @OneToMany
    List<Product> products;
}

