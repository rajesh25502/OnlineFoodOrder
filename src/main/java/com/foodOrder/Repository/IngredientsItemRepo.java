package com.foodOrder.Repository;

import com.foodOrder.Model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsItemRepo extends JpaRepository<IngredientsItem,Long> {
    public List<IngredientsItem> findByRestaurantId(Long id);
}
