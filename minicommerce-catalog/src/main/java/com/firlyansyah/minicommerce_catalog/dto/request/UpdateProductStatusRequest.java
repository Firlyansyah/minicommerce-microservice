package com.firlyansyah.minicommerce_catalog.dto.request;

import com.firlyansyah.minicommerce_catalog.entity.ProductStatus;

import jakarta.validation.constraints.*;

public record UpdateProductStatusRequest (

    @NotNull(message = "Status wajib diisi")
    ProductStatus status
    
){}
