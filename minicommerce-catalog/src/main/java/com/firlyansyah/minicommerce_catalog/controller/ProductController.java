package com.firlyansyah.minicommerce_catalog.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firlyansyah.minicommerce_catalog.dto.request.CreateProductRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStatusRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStockQuantityRequest;
import com.firlyansyah.minicommerce_catalog.dto.request.UpdateProductStockRequest;
import com.firlyansyah.minicommerce_catalog.dto.response.PagedResponse;
import com.firlyansyah.minicommerce_catalog.dto.response.ProductResponse;
import com.firlyansyah.minicommerce_catalog.entity.ProductStatus;
import com.firlyansyah.minicommerce_catalog.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Tag(name = "Catalog Service", description = "API untuk manajemen produk")
@RestController
@RequestMapping
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Create Product", description = "Membuat produk baru")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produk berhasil dibuat"),
            @ApiResponse(responseCode = "400", description = "Request tidak valid")
    })
    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @Operation(summary = "Get All Products", description = "Mengambil seluruh produk")
    @GetMapping("/api/products")
    public ResponseEntity<PagedResponse<ProductResponse>> getAll(

            @RequestParam(required = false) String name,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,

            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                service.getAll(name, status, minPrice, maxPrice, pageable));
    }

    @Operation(summary = "Get Product By ID", description = "Mengambil detail produk berdasarkan ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produk ditemukan"),
            @ApiResponse(responseCode = "404", description = "Produk tidak ditemukan")
    })
    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable @NotNull @Min(value = 1, message = "ID tidak valid") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Update Product Stock", description = "Mengubah stock produk secara langsung")
    @PatchMapping("api/products/{id}/stock")
    public ResponseEntity<ProductResponse> updateStock(
            @PathVariable @NotNull @Min(value = 1, message = "ID tidak valid") Long id,
            @Valid @RequestBody UpdateProductStockRequest request) {
        return ResponseEntity.ok(service.updateStock(id, request));
    }

    @Operation(summary = "Increase Product Stock", description = "Menambah stock produk")
    @PatchMapping("api/products/{id}/increase-stock")
    public ResponseEntity<ProductResponse> increaseStock(
            @PathVariable @NotNull @Min(value = 1, message = "ID tidak valid") Long id,
            @Valid @RequestBody UpdateProductStockQuantityRequest request) {
        return ResponseEntity.ok(service.increaseStock(id, request));
    }

    @Operation(summary = "Decrease Product Stock", description = "Mengurangi stock produk")
    @PatchMapping("api/products/{id}/decrease-stock")
    public ResponseEntity<ProductResponse> decreaseStock(
            @PathVariable @NotNull @Min(value = 1, message = "ID tidak valid") Long id,
            @Valid @RequestBody UpdateProductStockQuantityRequest request) {
        return ResponseEntity.ok(service.decreaseStock(id, request));
    }

    @Operation(summary = "Update Product Status", description = "Mengubah status produk ACTIVE atau INACTIVE")
    @PatchMapping("api/products/{id}/status")
    public ResponseEntity<ProductResponse> updateStatus(
            @PathVariable @NotNull @Min(value = 1, message = "ID tidak valid") Long id,
            @Valid @RequestBody UpdateProductStatusRequest request) {
        return ResponseEntity.ok(service.updateStatus(id, request));
    }

}
