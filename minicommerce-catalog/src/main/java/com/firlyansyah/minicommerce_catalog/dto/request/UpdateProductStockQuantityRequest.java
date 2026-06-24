package com.firlyansyah.minicommerce_catalog.dto.request;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateProductStockQuantityRequest(

    @NotNull
    @Positive(message = "Stock harus lebih dari 0")
    Integer quantity

) {}
