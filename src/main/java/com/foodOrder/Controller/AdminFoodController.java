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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String jwt)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantByID(request.getRestaurantID());
        Food food=foodService.createFood(request,request.getCategory(),restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK );
    }

    @PutMapping("{id}")
    public ResponseEntity<Food> updateFoodAvailability(@PathVariable Long id,
                                             @RequestHeader("Authorization") String jwt)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
