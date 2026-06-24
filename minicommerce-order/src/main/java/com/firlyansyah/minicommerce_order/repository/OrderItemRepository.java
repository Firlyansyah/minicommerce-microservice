package com.firlyansyah.minicommerce_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firlyansyah.minicommerce_order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}