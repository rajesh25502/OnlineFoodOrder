package com.foodOrder.Service;

import com.foodOrder.DTO.RestaurantDTO;
import com.foodOrder.Model.Restaurant;
import com.foodOrder.Model.User;
import com.foodOrder.Request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user);
    public Restaurant updateRestaurant(Long restaurantID, CreateRestaurantRequest updatedRestaurant)throws Exception;
    public void deleteRestaurant(Long restaurantID)throws Exception;
    public List<Restaurant> getAllRestaurant();
    public List<Restaurant> searchRestaurant(String keyword);
    public Restaurant findRestaurantByID(Long ID)throws Exception;
    public Restaurant getRestaurantByUserID(Long userID)throws Exception;
    public RestaurantDTO addToFavorite(Long restaurantID,User user)throws Exception;
    public Restaurant updateRestaurantStatus(Long ID)throws Exception;
}
