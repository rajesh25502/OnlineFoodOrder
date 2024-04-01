package com.foodOrder.Service;

import com.foodOrder.Model.IngredientCategory;
import com.foodOrder.Model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name,Long restaurantId)throws Exception;

    public IngredientCategory findIngredientCategotyById(Long id)throws Exception;

    public List<IngredientCategory> findIngredientCategotyByRestaurantId(Long restaurantId)throws Exception;

    public IngredientsItem createIngredientItem(String name,Long restaurantId, Long categoryId)throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId)throws Exception;

    public IngredientsItem updateStock(Long id)throws Exception;

}
