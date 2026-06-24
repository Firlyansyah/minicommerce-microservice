package com.firlyansyah.minicommerce_order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.firlyansyah.minicommerce_order.dto.catalog.request.UpdateProductStockQuantityRequest;
import com.firlyansyah.minicommerce_order.dto.catalog.response.ProductResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class CatalogClient {

    private final RestTemplate restTemplate;

    @Value("${catalog.service.url}")
    private String catalogServiceUrl;

    public ProductResponse getProduct(Long id) {
        return restTemplate.getForObject(catalogServiceUrl + "/api/products/" + id, ProductResponse.class);
    }

    public void increaseStock(Long id, Integer quantity) {

        restTemplate.exchange(
                catalogServiceUrl + "/api/products/" + id + "/increase-stock",
                HttpMethod.PATCH,
                new HttpEntity<>(new UpdateProductStockQuantityRequest(quantity)),
                Void.class);
    }

    public void decreaseStock(Long id, Integer quantity) {

        restTemplate.exchange(
                catalogServiceUrl + "/api/products/" + id + "/decrease-stock",
                HttpMethod.PATCH,
                new HttpEntity<>(new UpdateProductStockQuantityRequest(quantity)),
                Void.class);
    }

}
