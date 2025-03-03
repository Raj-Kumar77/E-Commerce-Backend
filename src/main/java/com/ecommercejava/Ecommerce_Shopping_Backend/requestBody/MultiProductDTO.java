package com.ecommercejava.Ecommerce_Shopping_Backend.requestBody;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MultiProductDTO {
    List<CreateProductDTO> products;
}
