package com.firlyansyah.minicommerce_catalog.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table (name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Positive(message = "Harga harus lebih dari 0")
    private Long price;

    @PositiveOrZero(message = "Stock tidak boleh negatif")
    private Integer stock;

    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    public Product () {}

    public long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getSku () {
        return sku;
    }

    public void setSku (String sku) {
        this.sku = sku;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Long getPrice () {
        return price;
    }

    public void setPrice (Long price) {
        this.price = price;
    }

    public Integer getStock () {
        return stock;
    }

    public void setStock (Integer stock) {
        this.stock = stock;
    }

    public ProductStatus getStatus () {
        return status;
    }
    
    public void setStatus (ProductStatus status) {
        this.status = status;
    }
}
