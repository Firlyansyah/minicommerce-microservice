package com.firlyansyah.minicommerce_order.exception;

public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException(
            Long id,
            String status) {
        super("Order dengan id " + id + " memiliki status " + status + " sehingga operasi tidak dapat dilakukan");
    }
}