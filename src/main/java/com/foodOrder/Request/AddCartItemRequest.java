package com.foodOrder.Request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {

    private Long FoodId;
    private int quantity;
    private List<String> ingredients;
}
