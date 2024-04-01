package com.foodOrder.Controller;

import com.foodOrder.Model.Restaurant;
import com.foodOrder.Model.User;
import com.foodOrder.Request.CreateRestaurantRequest;
import com.foodOrder.Response.MessageResponse;
import com.foodOrder.Service.RestaurantService;
import com.foodOrder.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String jwt)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.createRestaurant(req,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);
//        MessageResponse messageResponse=new MessageResponse();
//        messageResponse.setMessage("Restaurant deleted....");
        return new ResponseEntity<>("Restaurant deleted....", HttpStatus.OK);
    }
    @PutMapping("{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader("Authorization") String jwt,
                                                   @PathVariable Long id)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserID(@RequestHeader("Authorization") String jwt)throws Exception
    {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.getRestaurantByUserID(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
