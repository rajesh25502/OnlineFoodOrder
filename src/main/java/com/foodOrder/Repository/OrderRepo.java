package com.foodOrder.Repository;

import com.foodOrder.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    public List<Order> findByCustomerId(long userID);

    public List<Order> findByRestaurantId(long restaurantID);
}
