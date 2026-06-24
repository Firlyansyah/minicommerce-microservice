package com.firlyansyah.minicommerce_catalog.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateProductRequest (
    
    @NotBlank(message = "Sku wajib diisi")
    String sku,

    @NotBlank(message = "Nama wajib diisi")
    String name,

    @Min(value = 1, message = "Harga tidak boleh 0 atau negatif")
    Long price,

    @Min(value = 0, message = "Stock minimal 0")
    Integer stock
    
){}
