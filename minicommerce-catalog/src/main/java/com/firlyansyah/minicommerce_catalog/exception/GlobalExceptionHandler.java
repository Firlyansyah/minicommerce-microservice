package com.firlyansyah.minicommerce_catalog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validateArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of(
                "code", "VALIDATION_ERROR",
                "erros", errors));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        String errorMessage = ex.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        return ResponseEntity.badRequest().body(Map.of(
                "code", "VALIDATION_ERROR",
                "message", errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Format data tidak valid";

        if (ex.getCause() instanceof IllegalArgumentException) {
            message = ex.getCause().getMessage();
        }

        return ResponseEntity.badRequest().body(Map.of(
                "code", "INVALID_FORMAT",
                "message", message));
    }

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<?> duplicateSku(DuplicateSkuException ex) {
        return ResponseEntity.status(404).body(Map.of("code", "DUPLICATE_SKU", "message", ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of("code", "PRODUCT_NOT_FOUND", "message", ex.getMessage()));
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<?> notEnoughStock(NotEnoughStockException ex) {
        return ResponseEntity.status(404).body(Map.of("code", "NOT_ENOUGH_STOCK", "message", ex.getMessage()));
    }
}
