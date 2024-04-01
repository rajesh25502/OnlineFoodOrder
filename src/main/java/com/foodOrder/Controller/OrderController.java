package com.foodOrder.Controller;

import com.foodOrder.Model.CartItem;
import com.foodOrder.Model.Order;
import com.foodOrder.Model.User;
import com.foodOrder.Request.AddCartItemRequest;
import com.foodOrder.Request.OrderRequest;
import com.foodOrder.Response.PaymentResponse;
import com.foodOrder.Service.OrderService;
import com.foodOrder.Service.PaymentService;
import com.foodOrder.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest request,
                                             @RequestHeader("Authorization") String jwt)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        Order order=orderService.createOrder(request,user);
        PaymentResponse response=paymentService.createPaymentLink(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Order>> getUserOrderHistory(@RequestHeader("Authorization") String jwt)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        List<Order> orders=orderService.getUsersOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
