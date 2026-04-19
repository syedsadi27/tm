package com.tm.trophiesmementoes.backend.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tm.trophiesmementoes.backend.category.Category;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ProductDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record CategoryRef(String id, String slug, String label) {
        static CategoryRef from(Category c) {
            return c == null ? null : new CategoryRef(c.getId(), c.getSlug(), c.getLabel());
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ListItem(
            String id,
            String name,
            String slug,
            CategoryRef category,
            BigDecimal price,
            BigDecimal originalPrice,
            Integer discountPercent,
            String currency,
            Double rating,
            Integer reviewCount,
            Integer stock,
            String badge,
            String thumbnail,
            List<String> images,
            Boolean isBulkEligible,
            Instant createdAt
    ) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Detail(
            String id,
            String name,
            String slug,
            CategoryRef category,
            BigDecimal price,
            BigDecimal originalPrice,
            Integer discountPercent,
            String currency,
            Double rating,
            Integer reviewCount,
            Integer stock,
            String badge,
            String description,
            List<String> images,
            Boolean isBulkEligible,
            Integer bulkMinQty,
            String bulkContactPhone,
            String weight,
            String dimensions,
            String material,
            Instant createdAt,
            Instant updatedAt
    ) {}
}
