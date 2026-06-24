package com.firlyansyah.minicommerce_order.dto.catalog.response;

public record ProductResponse(
    Long id,
    String sku,
    String name,
    Long price,
    Integer stock,
    String status
) {}