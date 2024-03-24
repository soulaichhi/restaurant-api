package com.soulaichhi.onlinefoodorderingapi.repository;

import com.soulaichhi.onlinefoodorderingapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findCategoriesByRestaurantId(Long id);
}
