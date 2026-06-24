package com.firlyansyah.minicommerce_catalog.service;

import org.springframework.data.domain.Pageable;

import com.firlyansyah.minicommerce_catalog.dto.request.CreateProductRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStatusRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStockQuantityRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStockRequest;
import com.firlyansyah.minicommerce_catalog.dto.response.PagedResponse;
import com.firlyansyah.minicommerce_catalog.dto.response.ProductResponse;
import com.firlyansyah.minicommerce_catalog.entity.ProductStatus;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);

    PagedResponse<ProductResponse> getAll(String name, ProductStatus productStatus, Integer minPrice, Integer maxPrice,
            Pageable pageable);

    ProductResponse getById(Long id);

    ProductResponse updateStock(Long id, UpdateProductStockRequest request);

    ProductResponse increaseStock(long id, UpdateProductStockQuantityRequest request);

    ProductResponse decreaseStock(long id, UpdateProductStockQuantityRequest request);

    ProductResponse updateStatus(Long id, UpdateProductStatusRequest request);
}
