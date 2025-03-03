package com.ecommercejava.Ecommerce_Shopping_Backend.controller;

import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.InvalidProudctException;
import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.UserNotAllowed;
import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.UserNotFound;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.ApplicationOrder;
import com.ecommercejava.Ecommerce_Shopping_Backend.requestBody.PlaceOrderDTO;
import com.ecommercejava.Ecommerce_Shopping_Backend.responsebody.BillResponseBody;
import com.ecommercejava.Ecommerce_Shopping_Backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shopper")
public class ShopperController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO, @RequestParam String shopperEmail){

        try{
            BillResponseBody bill = orderService.placeOrder(placeOrderDTO, shopperEmail);
            return new ResponseEntity(bill, HttpStatus.CREATED);
        }catch (UserNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UserNotAllowed e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (InvalidProudctException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/order/history")
    public ResponseEntity getOrderById(@RequestParam String shopperEmail){
        try{

            List<ApplicationOrder> orders = orderService.getOrderByUser(shopperEmail);
            return new ResponseEntity(orders, HttpStatus.OK);

        }catch (UserNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UserNotAllowed e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/order/cancel")
    public void cancelOrder(@RequestParam String shopperEmail, @RequestParam UUID orderID){
        orderService.cancelOrder(shopperEmail, orderID);
    }
}
