package com.soulaichhi.onlinefoodorderingapi.request;

import com.soulaichhi.onlinefoodorderingapi.model.Category;
import com.soulaichhi.onlinefoodorderingapi.model.IngredientsItem;
import com.soulaichhi.onlinefoodorderingapi.model.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodReq {
    private String name;
    private String description;
    private Long price;
    private Category foodCategory;
    private List<String> images;
    private Long restaurantId;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private List<IngredientsItem> ingredients;
}
