package com.firlyansyah.minicommerce_catalog.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.firlyansyah.minicommerce_catalog.dto.request.CreateProductRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStatusRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStockQuantityRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStockRequest;
import com.firlyansyah.minicommerce_catalog.dto.response.PagedResponse;
import com.firlyansyah.minicommerce_catalog.dto.response.ProductResponse;
import com.firlyansyah.minicommerce_catalog.entity.Product;
import com.firlyansyah.minicommerce_catalog.entity.ProductStatus;
import com.firlyansyah.minicommerce_catalog.exception.DuplicateSkuException;
import com.firlyansyah.minicommerce_catalog.exception.NotEnoughStockException;
import com.firlyansyah.minicommerce_catalog.exception.ProductNotFoundException;
import com.firlyansyah.minicommerce_catalog.repository.ProductRepository;
import com.firlyansyah.minicommerce_catalog.service.ProductService;
import com.firlyansyah.minicommerce_catalog.specification.ProductSpecification;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getStatus());
    }

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public ProductResponse create(CreateProductRequest request) {
        if (repo.existsBySku(request.sku())) {
            throw new DuplicateSkuException(request.sku());
        }

        Product p = new Product();

        p.setSku(request.sku());
        p.setName(request.name());
        p.setPrice(request.price());
        p.setStock(request.stock());
        p.setStatus(ProductStatus.ACTIVE);

        Product saved = repo.save(p);

        return new ProductResponse(
                saved.getId(),
                saved.getSku(),
                saved.getName(),
                saved.getPrice(),
                saved.getStock(),
                saved.getStatus());
    }

    @Override
    public PagedResponse<ProductResponse> getAll(
            String name,
            ProductStatus status,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable) {

        Specification<Product> spec = Specification
                .where(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasStatus(status))
                .and(ProductSpecification.priceGreaterThan(minPrice))
                .and(ProductSpecification.priceLessThan(maxPrice));

        Page<Product> result = repo.findAll(spec, pageable);

        Page<ProductResponse> mapped = result.map(this::mapToResponse);

        return new PagedResponse<>(mapped);
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getStatus());
    }

    @Override
    public ProductResponse updateStock(Long id, UpdateProductStockRequest request) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setStock(request.stock());
        Product saved = repo.save(product);
        return new ProductResponse(
                saved.getId(),
                saved.getSku(),
                saved.getName(),
                saved.getPrice(),
                saved.getStock(),
                saved.getStatus());
    }

    @Override
    public ProductResponse increaseStock(long id, UpdateProductStockQuantityRequest request) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        Integer newStock = product.getStock() + request.quantity();
        product.setStock(newStock);
        Product saved = repo.save(product);
        return new ProductResponse(
                saved.getId(),
                saved.getSku(),
                saved.getName(),
                saved.getPrice(),
                saved.getStock(),
                saved.getStatus());
    }

    @Override
    public ProductResponse decreaseStock(long id, UpdateProductStockQuantityRequest request) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        Integer newStock = product.getStock() - request.quantity();
        if (newStock < 0) {
            throw new NotEnoughStockException(id);
        }
        product.setStock(newStock);
        Product saved = repo.save(product);
        return new ProductResponse(
                saved.getId(),
                saved.getSku(),
                saved.getName(),
                saved.getPrice(),
                saved.getStock(),
                saved.getStatus());
    }

    @Override
    public ProductResponse updateStatus(Long id, UpdateProductStatusRequest request) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setStatus(request.status());
        Product saved = repo.save(product);
        return new ProductResponse(
                saved.getId(),
                saved.getSku(),
                saved.getName(),
                saved.getPrice(),
                saved.getStock(),
                saved.getStatus());
    }
}
