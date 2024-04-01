package com.foodOrder.Service;

import com.foodOrder.Model.Order;
import com.foodOrder.Model.User;
import com.foodOrder.Request.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest request, User user)throws Exception;

    public Order updateOrder(Long irderID,String orderStatus)throws Exception;

    public Order cancelOrder(Long OrderId)throws Exception;

    public List<Order> getUsersOrders(Long userID)throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantID,String orderStatus)throws Exception;

    public Order findOrderById(Long orderID)throws Exception;
}
