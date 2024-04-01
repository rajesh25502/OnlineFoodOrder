package com.foodOrder.Service;

import com.foodOrder.Model.Category;
import com.foodOrder.Model.Food;
import com.foodOrder.Model.Restaurant;
import com.foodOrder.Repository.FoodRepo;
import com.foodOrder.Repository.RestaurantRepo;
import com.foodOrder.Request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;
    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food=new Food();

        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescriprion());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredientsItem(req.getIngredients());
        food.setSeasonable(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());

        Food savedFood=foodRepo.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodID) throws Exception {
        Food food=findFoodByID(foodID);
        food.setRestaurant(null);
        foodRepo.save(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantID, boolean isVegetarian,
                                        boolean isNonVeg, boolean isSeasonal, String foodCategory) {

        List<Food> food=foodRepo.findByRestaurantId(restaurantID);
        if(isVegetarian)
        {
            food=filterByVegetarian(food,isVegetarian);
        }
        if(isNonVeg)
        {
            food=filterByNonVeg(food,isNonVeg);
        }
        if(isSeasonal)
        {
            food=filterBySeasonal(food,isSeasonal);
        }
        if(foodCategory!=null && !foodCategory.equals(""))
        {
            food=filterByCategory(food,foodCategory);
        }
        return food;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory()!=null)
                    return food.getFoodCategory().getName().equals(foodCategory);
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(Food::isSeasonable).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food-> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food->food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepo.searchFood(keyword);
    }

    @Override
    public Food findFoodByID(Long foodID) throws Exception {
        Optional<Food> food=foodRepo.findById(foodID);
        if(food.isEmpty())
        {
            throw new Exception("Food does not exist with this id "+foodID);
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodID) throws Exception {
        Food food=findFoodByID(foodID);
        food.setAvailable(!food.isAvailable());
        return foodRepo.save(food);
    }
}
