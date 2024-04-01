package com.foodOrder.Request;

import lombok.Data;

@Data
public class IngredientItemRequest {

    private String name;
    private Long categoryID;
    private Long restaurantID;
}
