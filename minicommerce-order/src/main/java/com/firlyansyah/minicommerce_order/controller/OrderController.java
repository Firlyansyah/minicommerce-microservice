package com.firlyansyah.minicommerce_order.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firlyansyah.minicommerce_order.dto.order.request.CreateOrderRequest;
import com.firlyansyah.minicommerce_order.dto.order.response.OrderResponse;
import com.firlyansyah.minicommerce_order.dto.order.response.PagedResponse;
import com.firlyansyah.minicommerce_order.entity.OrderStatus;
import com.firlyansyah.minicommerce_order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Tag(name = "Order Service", description = "API untuk manajemen order")
@RestController
@RequestMapping
public class OrderController {

        private final OrderService service;

        public OrderController(OrderService service) {
                this.service = service;
        }

        @Operation(summary = "Create Order", description = "Membuat order baru")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Order berhasil dibuat"),
                        @ApiResponse(responseCode = "400", description = "Request tidak valid")
        })
        @PostMapping("/api/orders")
        public ResponseEntity<OrderResponse> createOrder(
                        @Valid @RequestBody CreateOrderRequest request) {

                return ResponseEntity.status(201)
                                .body(service.createOrder(request));
        }

        @Operation(summary = "Get All Orders", description = "Mengambil seluruh data order")
        @GetMapping("/api/orders")
        public ResponseEntity<PagedResponse<OrderResponse>> getOrders(
                        @RequestParam(required = false) String customerName,
                        @RequestParam(required = false) OrderStatus status,
                        @RequestParam(required = false) BigDecimal minAmount,
                        @RequestParam(required = false) BigDecimal maxAmount,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,

                        @RequestParam(defaultValue = "0") @Min(0) int page,
                        @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "asc") String sortDir) {

                Sort sort = sortDir.equalsIgnoreCase("desc")
                                ? Sort.by(sortBy).descending()
                                : Sort.by(sortBy).ascending();

                PageRequest pageable = PageRequest.of(page, size, sort);
                return ResponseEntity.ok(
                                service.getOrders(customerName, status, minAmount, maxAmount, startDate, endDate,
                                                pageable));
        }

        @Operation(summary = "Get Order By ID", description = "Mengambil detail order berdasarkan ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Order ditemukan"),
                        @ApiResponse(responseCode = "404", description = "Order tidak ditemukan")
        })
        @GetMapping("/api/orders/{id}")
        public ResponseEntity<OrderResponse> getOrder(
                        @PathVariable @NotNull @Min(value = 1, message = "ID tidak valid") Long id) {

                return ResponseEntity.ok(
                                service.getOrder(id));
        }

        @Operation(summary = "Pay Order", description = "Mengubah status order dari PENDING menjadi PAID")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Order berhasil dibayar"),
                        @ApiResponse(responseCode = "400", description = "Order bukan status PENDING")
        })
        @PatchMapping("/api/orders/{id}/pay")
        public ResponseEntity<OrderResponse> payOrder(
                        @PathVariable @NotNull @Min(value = 1, message = "ID tidak valid") Long id) {

                return ResponseEntity.ok(
                                service.payOrder(id));
        }

        @Operation(summary = "Cancel Order", description = "Membatalkan order dan mengembalikan stock")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Order berhasil dibatalkan"),
                        @ApiResponse(responseCode = "400", description = "Order bukan status PENDING")
        })
        @PatchMapping("/api/orders/{id}/cancel")
        public ResponseEntity<OrderResponse> cancelOrder(
                        @PathVariable @NotNull @Min(value = 1, message = "ID tidak valid") Long id) {

                return ResponseEntity.ok(
                                service.cancelOrder(id));
        }
}