package com.firlyansyah.minicommerce_order.dto.order.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotBlank
    @NotBlank(message = "Nama wajib diisi")
    private String customerName;

    @Email
    @NotBlank(message = "Email wajib diisi")
    private String customerEmail;

    @Valid
    @NotNull(message = "Items wajib diisi")
    @Size(min = 1, message = "Order tidak boleh kosong")
    private List<OrderItemRequest> items;
}