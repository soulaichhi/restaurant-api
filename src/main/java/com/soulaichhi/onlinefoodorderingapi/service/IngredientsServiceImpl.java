package com.soulaichhi.onlinefoodorderingapi.service;

import com.soulaichhi.onlinefoodorderingapi.model.IngredientCategory;
import com.soulaichhi.onlinefoodorderingapi.model.IngredientsItem;
import com.soulaichhi.onlinefoodorderingapi.model.Restaurant;
import com.soulaichhi.onlinefoodorderingapi.repository.IngredientCategoryRepository;
import com.soulaichhi.onlinefoodorderingapi.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService{
    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory=new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategoryOpt = ingredientCategoryRepository.findById(id);
        if (ingredientCategoryOpt.isEmpty()){
            throw  new Exception("IngredientCategory not found: " + id);
        }
        return ingredientCategoryOpt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);

    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception {

        return ingredientsItemRepository.findByRestaurantId(restaurantId);

    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);
        IngredientsItem ingredientsItem=new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(category);
        IngredientsItem ingredient =ingredientsItemRepository.save(ingredientsItem);
        category.getIngredients().add(ingredient);
        return ingredient;
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> ingredientOpt = ingredientsItemRepository.findById(id);
        if (ingredientOpt.isEmpty()){
            throw  new Exception("Ingredient not found: " + id);
        }

        IngredientsItem ingredientItem= ingredientOpt.get();
        ingredientItem.setInStock(!ingredientItem.isInStock());
        return ingredientsItemRepository.save(ingredientItem);
    }
}
