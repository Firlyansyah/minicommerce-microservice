package com.firlyansyah.minicommerce_catalog.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(Long id) {
        super("Stock tidak cukup untuk produk dengan id " + id);
    }
}
