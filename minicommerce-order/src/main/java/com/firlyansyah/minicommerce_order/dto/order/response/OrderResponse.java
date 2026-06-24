package com.firlyansyah.minicommerce_order.dto.order.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.firlyansyah.minicommerce_order.entity.OrderItem;
import com.firlyansyah.minicommerce_order.entity.OrderStatus;

public record OrderResponse(
        Long id,
        String customerName,
        String customerEmail,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createdAt,
        List<OrderItem> items) {
}
