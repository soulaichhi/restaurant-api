package com.soulaichhi.onlinefoodorderingapi.controller;

import com.soulaichhi.onlinefoodorderingapi.model.Food;
import com.soulaichhi.onlinefoodorderingapi.model.Restaurant;
import com.soulaichhi.onlinefoodorderingapi.model.User;
import com.soulaichhi.onlinefoodorderingapi.request.CreateFoodReq;
import com.soulaichhi.onlinefoodorderingapi.service.FoodService;
import com.soulaichhi.onlinefoodorderingapi.service.RestaurantService;
import com.soulaichhi.onlinefoodorderingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(keyword);
        return  new ResponseEntity<>(foods, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonveg,
                                                        @RequestParam(required = false) String foodCategory,
                                                        @PathVariable Long restaurantId,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantFoods(restaurantId,vegetarian,nonveg,seasonal,foodCategory);
        return  new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
