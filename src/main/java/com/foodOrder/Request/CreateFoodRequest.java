package com.foodOrder.Request;

import com.foodOrder.Model.Category;
import com.foodOrder.Model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String descriprion;
    private double price;
    private Category category;
    private List<String> images;
    private long restaurantID;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;

}
