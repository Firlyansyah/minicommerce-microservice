package com.firlyansyah.minicommerce_order.dto.catalog.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateProductStockQuantityRequest(

    @NotNull
    @Positive
    Integer quantity

) {}