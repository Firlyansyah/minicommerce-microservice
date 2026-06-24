package com.firlyansyah.minicommerce_catalog.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException (Long id) {
        super("Product dengan id " + id + " tidak ditemukan");
    }
}
