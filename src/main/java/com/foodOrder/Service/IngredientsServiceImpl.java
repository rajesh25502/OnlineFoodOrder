package com.foodOrder.Service;

import com.foodOrder.Model.IngredientCategory;
import com.foodOrder.Model.IngredientsItem;
import com.foodOrder.Model.Restaurant;
import com.foodOrder.Repository.IngredientsCategoryRepo;
import com.foodOrder.Repository.IngredientsItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    @Autowired
    private IngredientsCategoryRepo ingredientsCategoryRepo;

    @Autowired
    private IngredientsItemRepo ingredientsItemRepo;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantByID(restaurantId);
        IngredientCategory ingredientCategory=new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);

        return ingredientsCategoryRepo.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategotyById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory=ingredientsCategoryRepo.findById(id);
        if(ingredientCategory.isEmpty())
        {
            throw new Exception("Ingredient category not found");
        }
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategotyByRestaurantId(Long restaurantId) throws Exception {
        //restaurantService.findRestaurantByID(restaurantId) //To handle exception
        Optional<List<IngredientCategory>> ingredientCategory= Optional.ofNullable(ingredientsCategoryRepo.findByRestaurantId(restaurantId));
        if(ingredientCategory.isEmpty())
        {
            throw new Exception("Ingredient category not found");
        }
        return ingredientCategory.get();
    }

    @Override
    public IngredientsItem createIngredientItem(String name, Long restaurantId, Long categoryId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantByID(restaurantId);
        IngredientCategory ingredientCategory=findIngredientCategotyById(categoryId);

        IngredientsItem ingredientsItem=new IngredientsItem();
        ingredientsItem.setName(name);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientCategory);

        IngredientsItem savedIngredientsItem=ingredientsItemRepo.save(ingredientsItem);
        ingredientCategory.getIngredients().add(savedIngredientsItem);
        return savedIngredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {

        return ingredientsItemRepo.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> ingredientsItem=ingredientsItemRepo.findById(id);
        if(ingredientsItem.isEmpty())
        {
            throw new Exception("Ingredient Not found");
        }
        IngredientsItem ingredientsItems=ingredientsItem.get();
        ingredientsItems.setInStoke(!ingredientsItems.isInStoke());
        return ingredientsItemRepo.save(ingredientsItems);
    }
}
