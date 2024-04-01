package com.foodOrder.Service;

import com.foodOrder.Model.Category;
import com.foodOrder.Model.Restaurant;
import com.foodOrder.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public Category createCategory(String name, Long UserID)throws Exception {

        Restaurant restaurant=restaurantService.getRestaurantByUserID(UserID);
        Category category=new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepo.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserID(id);
        return categoryRepo.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category=categoryRepo.findById(id);
        if(category.isEmpty())
        {
            throw new Exception("Not able to find the category by id : "+id);
        }
        return category.get();
    }
}
