package com.firlyansyah.minicommerce_catalog.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateProductStockRequest (

    @NotNull(message = "Stock wajib diisi")
    @Min(value = 0, message = "Stock minimal 0")
    Integer stock
    
){}
