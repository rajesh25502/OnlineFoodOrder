package com.foodOrder.Controller;

import com.foodOrder.Model.IngredientCategory;
import com.foodOrder.Model.IngredientsItem;
import com.foodOrder.Model.User;
import com.foodOrder.Request.IngredientCategoryRequest;
import com.foodOrder.Request.IngredientItemRequest;
import com.foodOrder.Service.IngredientsService;
import com.foodOrder.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private UserService userService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request,
                                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(request.getName(), request.getRestaurantID());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping("/item")
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest request,
                                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        IngredientsItem ingredientItem = ingredientsService.createIngredientItem(request.getName(), request.getRestaurantID(), request.getCategoryID());
        return new ResponseEntity<>(ingredientItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    private ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id,
                                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        IngredientsItem ingredientItem = ingredientsService.updateStock(id);
        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    private ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable Long id,
                                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<IngredientsItem> ingredientItem = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    private ResponseEntity<List<IngredientCategory>> getRestaurantIngredientsCategory(@PathVariable Long id,
                                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<IngredientCategory> ingredientCategory = ingredientsService.findIngredientCategotyByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
    }
}
