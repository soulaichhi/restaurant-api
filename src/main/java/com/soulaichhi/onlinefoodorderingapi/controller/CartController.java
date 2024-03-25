package com.soulaichhi.onlinefoodorderingapi.controller;

import com.soulaichhi.onlinefoodorderingapi.model.Cart;
import com.soulaichhi.onlinefoodorderingapi.model.CartItem;
import com.soulaichhi.onlinefoodorderingapi.model.User;
import com.soulaichhi.onlinefoodorderingapi.request.AddCartItemRequest;
import com.soulaichhi.onlinefoodorderingapi.request.UpdateCartItemRequest;
import com.soulaichhi.onlinefoodorderingapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;
    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(

            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.clearCart(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(

            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.findCartByUserId(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
