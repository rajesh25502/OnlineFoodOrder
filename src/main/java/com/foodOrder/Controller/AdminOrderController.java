package com.foodOrder.Controller;

import com.foodOrder.Model.Order;
import com.foodOrder.Model.User;
import com.foodOrder.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/{restaurantID}")
    public ResponseEntity<List<Order>> getRestaurantOrderHistory(@PathVariable Long restaurantID,
            @RequestParam(required = false)String orderStatus)throws Exception
    {
        //User user=userService.findUserByJwtToken(jwt);
        List<Order> orders=orderService.getRestaurantOrders(restaurantID,orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PutMapping("/{orderID}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderID,
                                                         @PathVariable String orderStatus)throws Exception
    {
        //User user=userService.findUserByJwtToken(jwt);
        Order orders=orderService.updateOrder(orderID,orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
