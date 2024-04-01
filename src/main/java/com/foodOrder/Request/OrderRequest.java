package com.foodOrder.Request;

import com.foodOrder.Model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantID;
    private Address deliveryAddress;

}
