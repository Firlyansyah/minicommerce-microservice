package com.firlyansyah.minicommerce_order.specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.firlyansyah.minicommerce_order.entity.Order;
import com.firlyansyah.minicommerce_order.entity.OrderStatus;

public class OrderSpecification {

    public static Specification<Order> hasCustomerName(String name) {
        return (root, query, cb) -> name == null ? null
                : cb.like(cb.lower(root.get("customerName")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Order> totalAmountGreaterThan(BigDecimal minAmount) {
        return (root, query, cb) -> minAmount == null ? null
                : cb.greaterThanOrEqualTo(root.get("totalAmount"), minAmount);
    }

    public static Specification<Order> totalAmountLessThan(BigDecimal maxAmount) {
        return (root, query, cb) -> maxAmount == null ? null : cb.lessThanOrEqualTo(root.get("totalAmount"), maxAmount);
    }

    public static Specification<Order> createdAfter(LocalDateTime startDate) {
        return (root, query, cb) -> startDate == null ? null
                : cb.greaterThanOrEqualTo(root.get("createdAt"), startDate);
    }

    public static Specification<Order> createdBefore(LocalDateTime endDate) {
        return (root, query, cb) -> endDate == null ? null : cb.lessThanOrEqualTo(root.get("createdAt"), endDate);
    }
}