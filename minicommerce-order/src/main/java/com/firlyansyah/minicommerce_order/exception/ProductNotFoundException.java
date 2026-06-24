package com.firlyansyah.minicommerce_order.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product dengan id " + id + " tidak ditemukan");
    }
}
