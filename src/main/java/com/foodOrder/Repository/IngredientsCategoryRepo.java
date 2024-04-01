package com.foodOrder.Repository;

import com.foodOrder.Model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsCategoryRepo extends JpaRepository<IngredientCategory,Long> {
    public List<IngredientCategory> findByRestaurantId(Long id);
}
