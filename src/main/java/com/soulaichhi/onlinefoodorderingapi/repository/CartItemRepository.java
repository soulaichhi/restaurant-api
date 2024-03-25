package com.soulaichhi.onlinefoodorderingapi.repository;

import com.soulaichhi.onlinefoodorderingapi.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
