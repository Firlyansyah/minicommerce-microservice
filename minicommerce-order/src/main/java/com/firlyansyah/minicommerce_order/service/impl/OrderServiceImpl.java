package com.firlyansyah.minicommerce_order.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.firlyansyah.minicommerce_order.client.CatalogClient;
import com.firlyansyah.minicommerce_order.dto.catalog.response.ProductResponse;
import com.firlyansyah.minicommerce_order.dto.order.request.CreateOrderRequest;
import com.firlyansyah.minicommerce_order.dto.order.request.OrderItemRequest;
import com.firlyansyah.minicommerce_order.dto.order.response.OrderResponse;
import com.firlyansyah.minicommerce_order.dto.order.response.PagedResponse;
import com.firlyansyah.minicommerce_order.entity.Order;
import com.firlyansyah.minicommerce_order.entity.OrderItem;
import com.firlyansyah.minicommerce_order.entity.OrderStatus;
import com.firlyansyah.minicommerce_order.exception.InvalidOrderStatusException;
import com.firlyansyah.minicommerce_order.exception.InvalidProductStatusException;
import com.firlyansyah.minicommerce_order.exception.NotEnoughStockException;
import com.firlyansyah.minicommerce_order.exception.OrderNotFoundException;
import com.firlyansyah.minicommerce_order.exception.ProductNotFoundException;
import com.firlyansyah.minicommerce_order.mapper.OrderMapper;
import com.firlyansyah.minicommerce_order.repository.OrderRepository;
import com.firlyansyah.minicommerce_order.service.OrderService;
import com.firlyansyah.minicommerce_order.specification.OrderSpecification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final CatalogClient catalogClient;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = new Order();

        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {

            ProductResponse product = catalogClient.getProduct(
                    itemRequest.getProductId());

            if (product == null) {
                throw new ProductNotFoundException(itemRequest.getProductId());
            }

            if (!"ACTIVE".equals(product.status())) {
                throw new InvalidProductStatusException(product.id(), product.name());
            }

            if (product.stock() < itemRequest.getQuantity()) {
                throw new NotEnoughStockException(product.id(), product.name());
            }

            BigDecimal price = BigDecimal.valueOf(product.price());

            BigDecimal subtotal = price.multiply(
                    BigDecimal.valueOf(
                            itemRequest.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .productId(product.id())
                    .productName(product.name())
                    .productPrice(price)
                    .quantity(itemRequest.getQuantity())
                    .subtotal(subtotal)
                    .order(order)
                    .build();

            orderItems.add(orderItem);

            totalAmount = totalAmount.add(subtotal);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = repo.save(order);

        for (OrderItem item : savedOrder.getItems()) {

            catalogClient.decreaseStock(
                    item.getProductId(),
                    item.getQuantity());
        }

        return OrderMapper.mapToResponse(savedOrder);
    }

    @Override
    @Transactional
    public PagedResponse<OrderResponse> getOrders(
            String customerName,
            OrderStatus status,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {

        LocalDateTime startDateTime = startDate != null
                ? startDate.atStartOfDay()
                : null;
        LocalDateTime endDateTime = endDate != null
                ? endDate.atTime(23, 59, 59)
                : null;
        Specification<Order> spec = Specification
                .where(OrderSpecification.hasCustomerName(customerName))
                .and(OrderSpecification.hasStatus(status))
                .and(OrderSpecification.totalAmountLessThan(minAmount))
                .and(OrderSpecification.totalAmountGreaterThan(maxAmount))
                .and(OrderSpecification.createdAfter(startDateTime))
                .and(OrderSpecification.createdBefore(endDateTime));

        Page<Order> result = repo.findAll(spec, pageable);
        Page<OrderResponse> mapped = result.map(OrderMapper::mapToResponse);

        return new PagedResponse<>(mapped);
    }

    @Override
    @Transactional
    public OrderResponse getOrder(Long id) {
        Order order = repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return OrderMapper.mapToResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse payOrder(Long id) {

        Order order = repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException(id, order.getStatus().name());
        }

        order.setStatus(OrderStatus.PAID);
        Order saved = repo.save(order);
        return OrderMapper.mapToResponse(saved);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long id) {

        Order order = repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException(id, order.getStatus().name());
        }

        for (OrderItem item : order.getItems()) {
            catalogClient.increaseStock(
                    item.getProductId(),
                    item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);
        Order saved = repo.save(order);
        return OrderMapper.mapToResponse(saved);
    }

}