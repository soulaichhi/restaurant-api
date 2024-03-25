package com.soulaichhi.onlinefoodorderingapi.service;

import com.soulaichhi.onlinefoodorderingapi.model.Cart;
import com.soulaichhi.onlinefoodorderingapi.model.CartItem;
import com.soulaichhi.onlinefoodorderingapi.model.Food;
import com.soulaichhi.onlinefoodorderingapi.model.User;
import com.soulaichhi.onlinefoodorderingapi.repository.CartItemRepository;
import com.soulaichhi.onlinefoodorderingapi.repository.CartRepository;
import com.soulaichhi.onlinefoodorderingapi.repository.FoodRepository;
import com.soulaichhi.onlinefoodorderingapi.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Food food = foodService.findFoodById(req.getFoodId());
        for (CartItem cartItem:cart.getItems()){
            if (cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }
        CartItem newCartItem= new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());
        CartItem savedCartItem= cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw  new Exception("CartItem not found: " + cartItemId);
        }
        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw  new Exception("CartItem not found: " + cartItemId);
        }
        CartItem item = cartItemOptional.get();
        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long totalPrice = 0L;
        for (CartItem cartItem:cart.getItems()){
            totalPrice += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty()){
            throw  new Exception("Cart not found: " + id);
        }
        return cartOptional.get();
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return cartRepository.findByCustomerId(user.getId());
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
