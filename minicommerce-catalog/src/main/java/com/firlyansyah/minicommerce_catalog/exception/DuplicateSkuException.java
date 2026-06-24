package com.firlyansyah.minicommerce_catalog.exception;

public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(String sku) {
        super("SKU " + sku + " sudah dipakai");
    }
}
