package com.foodOrder.Service;

import com.foodOrder.Model.Category;
import com.foodOrder.Model.Food;
import com.foodOrder.Model.Restaurant;
import com.foodOrder.Request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    public void deleteFood(Long foodID)throws Exception;

    public List<Food> getRestaurantFood(Long restaurantID, boolean isVegetarian,  boolean isNonVeg, boolean isSeasonal, String category);

    public List<Food> searchFood(String keyword);

    public Food findFoodByID(Long foodID)throws Exception;

    public Food updateAvailabilityStatus(Long foodID)throws Exception;
}
