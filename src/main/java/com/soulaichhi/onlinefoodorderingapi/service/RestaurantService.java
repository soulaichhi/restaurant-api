package com.soulaichhi.onlinefoodorderingapi.service;

import com.soulaichhi.onlinefoodorderingapi.dto.RestaurantDto;
import com.soulaichhi.onlinefoodorderingapi.model.Restaurant;
import com.soulaichhi.onlinefoodorderingapi.model.User;
import com.soulaichhi.onlinefoodorderingapi.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);
    public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedReq) throws Exception;
    public void deleteRestaurant(Long restaurantId) throws Exception;
    public List<Restaurant> getAllRestaurants();
    public List<Restaurant> searchRestaurant(String keyWord);
    public Restaurant findRestaurantById(Long id) throws Exception;
    public Restaurant getRestaurantByUserId(Long userId) throws Exception;
    public RestaurantDto addToFavorites(Long restaurantId,User user) throws Exception;
    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
