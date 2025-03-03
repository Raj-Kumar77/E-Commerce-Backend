package com.ecommercejava.Ecommerce_Shopping_Backend.requestBody;

import lombok.Data;

import java.util.UUID;

@Data
public class SingleProductOrderDTO {
    UUID pid;
    int quantity;
}
