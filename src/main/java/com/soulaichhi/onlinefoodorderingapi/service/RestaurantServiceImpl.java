package com.soulaichhi.onlinefoodorderingapi.service;

import com.soulaichhi.onlinefoodorderingapi.dto.RestaurantDto;
import com.soulaichhi.onlinefoodorderingapi.model.Address;
import com.soulaichhi.onlinefoodorderingapi.model.Restaurant;
import com.soulaichhi.onlinefoodorderingapi.model.User;
import com.soulaichhi.onlinefoodorderingapi.repository.AddressRepository;
import com.soulaichhi.onlinefoodorderingapi.repository.RestaurantRepository;
import com.soulaichhi.onlinefoodorderingapi.repository.UserRepository;
import com.soulaichhi.onlinefoodorderingapi.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(req.getName());
        restaurant.setImages(req.getImages());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setOwner(user);
        restaurant.setRegistrationDate(LocalDateTime.now());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedReq) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant.getName()!=null){
            restaurant.setName(updatedReq.getName());
        }
        if (restaurant.getImages()!=null){
            restaurant.setDescription(updatedReq.getDescription());
        }
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedReq.getCuisineType());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyWord) {

        return restaurantRepository.findBySearchQuery(keyWord);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if (opt.isEmpty()){
            throw new Exception("Restaurant not found with id:" + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant==null){
            throw new Exception("Restaurant not found with Owner id:" + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setTitle(restaurant.getName());
        dto.setImages(restaurant.getImages());
        dto.setId(restaurantId);
        if (user.getFavorites().contains(dto)){
            user.getFavorites().remove(dto);
        }else {
            user.getFavorites().add(dto);
        }
        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
