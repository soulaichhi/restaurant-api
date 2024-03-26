package com.soulaichhi.onlinefoodorderingapi.repository;

import com.soulaichhi.onlinefoodorderingapi.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
