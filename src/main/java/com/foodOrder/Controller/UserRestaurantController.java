package com.foodOrder.Controller;

import com.foodOrder.DTO.RestaurantDTO;
import com.foodOrder.Model.Restaurant;
import com.foodOrder.Model.User;
import com.foodOrder.Request.CreateRestaurantRequest;
import com.foodOrder.Service.RestaurantService;
import com.foodOrder.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class UserRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,
                                                                @RequestParam String keyword)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant=restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant=restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantByID(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long id)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantByID(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<RestaurantDTO> addFavorite(@RequestHeader("Authorization") String jwt,
                                                     @PathVariable Long id)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        RestaurantDTO restaurant=restaurantService.addToFavorite(id,user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
