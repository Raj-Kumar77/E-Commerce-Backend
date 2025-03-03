package com.ecommercejava.Ecommerce_Shopping_Backend.requestBody;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateProductDTO {
    String productName;
    String productCategory;
    int price;
    int quantity;
}
