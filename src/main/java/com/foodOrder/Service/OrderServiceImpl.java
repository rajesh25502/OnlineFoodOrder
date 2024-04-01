package com.foodOrder.Service;

import com.foodOrder.Model.*;
import com.foodOrder.Repository.*;
import com.foodOrder.Request.OrderRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest request, User user) throws Exception {

        Address shippingAddress=request.getDeliveryAddress();
        Address savedAddress=addressRepo.save(shippingAddress);
        if(!user.getAddress().contains(savedAddress))
        {
            user.getAddress().add(savedAddress);
            userRepo.save(user);
        }

        Restaurant restaurant=restaurantService.findRestaurantByID(request.getRestaurantID());
        Order createdOrder=new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(shippingAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserID(user.getId());

        List<OrderItem> orderItems=new ArrayList<>();
        for(CartItem cartItem: cart.getCartItem())
        {
             OrderItem orderItem=new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem=orderItemRepo.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotal());

        Order savedOrder=orderRepo.save(createdOrder);
        restaurant.getOrders().add(savedOrder);
        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderID, String orderStatus) throws Exception {
        Order order=findOrderById(orderID);
        if(orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING"))
        {
            order.setOrderStatus(orderStatus);
            return orderRepo.save(order);
        }
        throw new Exception("Select a valid order status");
    }

    @Override
    public Order cancelOrder(Long orderId) throws Exception {
        Order order=findOrderById(orderId);
        orderRepo.deleteById(orderId);
        return order;
    }

    @Override
    public Order findOrderById(Long orderID)throws Exception
    {
        Optional<Order> order=orderRepo.findById(orderID);
        if(order.isEmpty())
        {
            throw new Exception("Order not found with orderID");
        }
        return order.get();
    }
    @Override
    public List<Order> getUsersOrders(Long userID) throws Exception {
        Optional<List<Order>> orders= Optional.ofNullable(orderRepo.findByCustomerId(userID));
        if(orders.isEmpty())
        {
            throw new Exception("Order not found with user id");
        }
        return orders.get();
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantID, String orderStatus) throws Exception {
        Optional<List<Order>> orders= Optional.ofNullable(orderRepo.findByRestaurantId(restaurantID));
        if(orders.isEmpty())
        {
            throw new Exception("Order not found with user id");
        }
        List<Order> fetchedOrders=orders.get();
        if(orderStatus!=null)
            fetchedOrders=fetchedOrders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        return fetchedOrders;
    }
}
