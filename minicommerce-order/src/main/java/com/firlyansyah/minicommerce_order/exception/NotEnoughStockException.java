package com.firlyansyah.minicommerce_order.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(Long id, String name) {
        super("Stock tidak cukup untuk produk " + name + " dengan id " + id);
    }
}
