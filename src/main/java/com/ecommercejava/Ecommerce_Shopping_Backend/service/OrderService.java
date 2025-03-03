package com.ecommercejava.Ecommerce_Shopping_Backend.service;

import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.InvalidProudctException;
import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.UserNotAllowed;
import com.ecommercejava.Ecommerce_Shopping_Backend.exceptions.UserNotFound;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.ApplicationOrder;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.ApplicationUser;
import com.ecommercejava.Ecommerce_Shopping_Backend.model.Product;
import com.ecommercejava.Ecommerce_Shopping_Backend.repository.OrderRepository;
import com.ecommercejava.Ecommerce_Shopping_Backend.repository.ProductRepository;
import com.ecommercejava.Ecommerce_Shopping_Backend.requestBody.PlaceOrderDTO;
import com.ecommercejava.Ecommerce_Shopping_Backend.requestBody.SingleProductOrderDTO;
import com.ecommercejava.Ecommerce_Shopping_Backend.responsebody.BillProductDTO;
import com.ecommercejava.Ecommerce_Shopping_Backend.responsebody.BillResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    UserUtil userUtil;
    @Autowired
    ProductUtil productUtil;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MailService mailService;

    public List<ApplicationOrder> getOrderByUser(String shopperEmail){
        ApplicationUser shopper = userUtil.checkEmailExist(shopperEmail);
        if(shopper == null){
            throw new UserNotFound(String.format("User with email %s does not exist", shopperEmail));
        }

        boolean isSeller = userUtil.isSeller(shopper);
        if(isSeller){
            throw new UserNotAllowed(String.format("User with email %s is not allowed to perform this action", shopperEmail));
        }

        List<ApplicationOrder> orders = orderRepository.getOrderByShopperId(shopper.getId());
        return orders;
    }

    public void cancelOrder(String shopperEmail, UUID orderID){
//        ApplicationUser shopper = userUtil.checkEmailExist(shopperEmail);
//        if(shopper == null){
//            throw new UserNotFound(String.format("User with email %s does not exist", shopperEmail));
//        }
//
//        boolean isSeller = userUtil.isSeller(shopper);
//        if(isSeller){
//            throw new UserNotAllowed(String.format("User with email %s is not allowed to perform this action", shopperEmail));
//        }

        //validate orderID before this
        orderRepository.cancelStatus(orderID);

        //mail user and seller regarding cancellation
        try{
            mailService.cancelOrder(shopperEmail, orderID.toString());
        }catch(Exception e){

        }
    }

    public BillResponseBody placeOrder(PlaceOrderDTO placeOrderDTO, String shopperEmail){

        ApplicationUser user = userUtil.checkEmailExist(shopperEmail);
        if(user == null){
            throw new UserNotFound(String.format("Shopper with email %s does not exist", shopperEmail));
        }

        boolean isSeller = userUtil.isSeller(user);
        if(isSeller){
            throw new UserNotAllowed(String.format("Seller with email %s is not allowed to place order", shopperEmail));
        }

        List<SingleProductOrderDTO> products = placeOrderDTO.getProducts();

        ApplicationOrder order = new ApplicationOrder();
        order.setShopper(user);
        Date currentDate = new Date();
        order.setOrderPlaced(currentDate);
        Date expectedDate = new Date(currentDate.getDate() + 7);
        order.setExpectedDelivery(expectedDate);
        order.setStatus("PENDING");

        int totalAmount = 0;
        int totalItems = 0;
        List<Product> originalProducts = new ArrayList<>();
        BillResponseBody billResponseBody = new BillResponseBody();
        List<BillProductDTO> billProductDTOS = new ArrayList<>();

        for(SingleProductOrderDTO product : products){
            UUID pid = product.getPid();
            Product originalProuduct = productUtil.isValidProductID(pid);
            if(originalProuduct == null){
                throw new InvalidProudctException(String.format("Product with id %s does not exist", pid.toString()));
            }

            int totalQuantity = originalProuduct.getQuantity();
            if(totalQuantity <= 0){
                throw new InvalidProudctException(String.format("Product with id %s does not have sufficient quantity", pid.toString()));
            }

            BillProductDTO billProductDTO = new BillProductDTO();
            billProductDTO.setProductID(originalProuduct.getId());
            billProductDTO.setQuantity(product.getQuantity());
            billProductDTO.setTotalPrice(originalProuduct.getPrice()*product.getQuantity());
            billProductDTO.setSupplierName(originalProuduct.getSeller().getName());
            billProductDTO.setProductName(originalProuduct.getName());
            billProductDTOS.add(billProductDTO);


            originalProducts.add(originalProuduct);
            totalItems += product.getQuantity();
            totalAmount += originalProuduct.getPrice()*product.getQuantity();

            int finalQuantity = totalQuantity - product.getQuantity();
            productRepository.updateProductQuantity(finalQuantity, pid);

            int totalQuantitySold = originalProuduct.getQuantitySold() + product.getQuantity();
            productRepository.updateTotalProductQuantity(totalQuantitySold, pid);
        }

        order.setTotalQuantity(totalItems);
        order.setTotalAmount(totalAmount);
        order.setProducts(originalProducts);
        order.setPaymentMethod(placeOrderDTO.getPaymentMethod().toString());
        orderRepository.save(order);

        billResponseBody.setOrderID(order.getId());
        billResponseBody.setProducts(billProductDTOS);
        billResponseBody.setTotalPrice(totalAmount);
        billResponseBody.setOrderPlacedDate(order.getOrderPlaced());
        billResponseBody.setOrderExpectedDate(order.getExpectedDelivery());
        billResponseBody.setShopperEmail(user.getEmail());
        billResponseBody.setTotalQuantity(totalItems);
        return billResponseBody;
    }
}
