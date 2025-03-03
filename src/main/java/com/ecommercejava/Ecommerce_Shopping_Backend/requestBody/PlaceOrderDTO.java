package com.ecommercejava.Ecommerce_Shopping_Backend.requestBody;

import com.ecommercejava.Ecommerce_Shopping_Backend.enums.PaymentMethod;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderDTO {
    List<SingleProductOrderDTO> products;
    PaymentMethod paymentMethod;
}
