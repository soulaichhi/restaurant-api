package com.soulaichhi.onlinefoodorderingapi.controller;

import com.soulaichhi.onlinefoodorderingapi.model.Food;
import com.soulaichhi.onlinefoodorderingapi.model.Restaurant;
import com.soulaichhi.onlinefoodorderingapi.model.User;
import com.soulaichhi.onlinefoodorderingapi.request.CreateFoodReq;
import com.soulaichhi.onlinefoodorderingapi.response.MessageResponse;
import com.soulaichhi.onlinefoodorderingapi.service.FoodService;
import com.soulaichhi.onlinefoodorderingapi.service.RestaurantService;
import com.soulaichhi.onlinefoodorderingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/foods")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodReq req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req,req.getFoodCategory(),restaurant);
        return  new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(
                                           @RequestHeader("Authorization") String jwt,
                                           @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse messageResponse=new MessageResponse();
        messageResponse.setMessage("food deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailability(@PathVariable Long id,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);
        return  new ResponseEntity<>(food, HttpStatus.OK);
    }


}
