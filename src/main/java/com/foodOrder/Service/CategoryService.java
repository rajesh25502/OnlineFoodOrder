package com.foodOrder.Service;

import com.foodOrder.Model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name,Long UserID)throws Exception;

    public List<Category> findCategoryByRestaurantId(Long id)throws Exception;

    public Category findCategoryById(Long id)throws Exception;
}
