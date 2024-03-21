package com.soulaichhi.onlinefoodorderingapi.repository;

import com.soulaichhi.onlinefoodorderingapi.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
