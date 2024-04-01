package com.foodOrder.Controller;

import com.foodOrder.Model.Food;
import com.foodOrder.Model.Restaurant;
import com.foodOrder.Model.User;
import com.foodOrder.Request.CreateFoodRequest;
import com.foodOrder.Service.FoodService;
import com.foodOrder.Service.RestaurantService;
import com.foodOrder.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestHeader("Authorization") String jwt,
                                                             @RequestParam String keyword)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        List<Food> food=foodService.searchFood(keyword);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id, @RequestParam boolean vegetarian,
                                                        @RequestParam boolean seasonal,@RequestParam boolean nonveg,
                                                        @RequestParam(required = false) String foodcategory)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        List<Food> food=foodService.getRestaurantFood(id,vegetarian,nonveg,seasonal,foodcategory);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
