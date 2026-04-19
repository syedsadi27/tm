package com.tm.trophiesmementoes.backend.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "product_stats")
@Getter @Setter
public class ProductStats {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating;

    @Column(name = "review_count")
    private Integer reviewCount = 0;
}
