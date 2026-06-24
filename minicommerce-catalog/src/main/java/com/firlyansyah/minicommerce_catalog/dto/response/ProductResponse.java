package com.firlyansyah.minicommerce_catalog.dto.response;

import com.firlyansyah.minicommerce_catalog.entity.ProductStatus;

public record ProductResponse(
                Long id,
                String sku,
                String name,
                Long price,
                Integer stock,
                ProductStatus status) {

}