package com.soulaichhi.onlinefoodorderingapi.controller;

import com.soulaichhi.onlinefoodorderingapi.dto.RestaurantDto;
import com.soulaichhi.onlinefoodorderingapi.model.Restaurant;
import com.soulaichhi.onlinefoodorderingapi.model.User;
import com.soulaichhi.onlinefoodorderingapi.service.RestaurantService;
import com.soulaichhi.onlinefoodorderingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
            @RequestHeader String jwt,
            @RequestParam String keyword) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);

    }
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestHeader String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(
            @RequestHeader String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addRestaurantToFavorites(
            @RequestHeader String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto restaurant = restaurantService.addToFavorites(id, user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }
}
