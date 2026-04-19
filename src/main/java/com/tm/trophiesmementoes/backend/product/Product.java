package com.tm.trophiesmementoes.backend.product;

import com.tm.trophiesmementoes.backend.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
@Getter @Setter
public class Product {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(length = 10)
    private String currency = "USD";

    private Integer stock = 0;
    private String badge;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String weight;
    private String dimensions;
    private String material;

    @Column(name = "is_bulk_eligible")
    private Boolean isBulkEligible = false;

    @Column(name = "bulk_min_qty")
    private Integer bulkMinQty;

    @Column(name = "bulk_contact_phone")
    private String bulkContactPhone;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
