package com.firlyansyah.minicommerce_order.mapper;

import com.firlyansyah.minicommerce_order.dto.order.response.OrderResponse;
import com.firlyansyah.minicommerce_order.entity.Order;

public final class OrderMapper {

    private OrderMapper() {
    }

    public static OrderResponse mapToResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getItems());
    }
}