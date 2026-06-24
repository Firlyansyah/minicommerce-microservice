package com.firlyansyah.minicommerce_order.exception;

public class InvalidProductStatusException extends RuntimeException {
    public InvalidProductStatusException(Long id, String name) {
        super("Product " + name + " dengan id " + id + " memiliki status tidak aktif");
    }
}
