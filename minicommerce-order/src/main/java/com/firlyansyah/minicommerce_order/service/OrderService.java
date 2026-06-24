package com.firlyansyah.minicommerce_order.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Pageable;

import com.firlyansyah.minicommerce_order.dto.order.request.CreateOrderRequest;
import com.firlyansyah.minicommerce_order.dto.order.response.OrderResponse;
import com.firlyansyah.minicommerce_order.dto.order.response.PagedResponse;
import com.firlyansyah.minicommerce_order.entity.OrderStatus;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    PagedResponse<OrderResponse> getOrders(String customerName, OrderStatus status, BigDecimal minAmount,
            BigDecimal maxAmount, LocalDate startDate, LocalDate endDate, Pageable pageable);

    OrderResponse getOrder(Long id);

    OrderResponse payOrder(Long id);

    OrderResponse cancelOrder(Long id);
}