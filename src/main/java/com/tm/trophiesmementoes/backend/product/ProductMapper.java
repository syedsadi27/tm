package com.tm.trophiesmementoes.backend.product;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    ProductDto.ListItem toListItem(Product p, ProductStats stats, String thumbnail, List<String> images) {
        return new ProductDto.ListItem(
                p.getId(), p.getName(), p.getSlug(),
                ProductDto.CategoryRef.from(p.getCategory()),
                p.getPrice(), p.getOriginalPrice(), p.getDiscountPercent(), p.getCurrency(),
                stats != null && stats.getAverageRating() != null ? stats.getAverageRating().doubleValue() : null,
                stats != null ? stats.getReviewCount() : null,
                p.getStock(), p.getBadge(), thumbnail, images,
                p.getStock() != null && p.getStock() >= 10,
                p.getCreatedAt()
        );
    }

    ProductDto.Detail toDetail(Product p, ProductStats stats, List<String> images) {
        return new ProductDto.Detail(
                p.getId(), p.getName(), p.getSlug(),
                ProductDto.CategoryRef.from(p.getCategory()),
                p.getPrice(), p.getOriginalPrice(), p.getDiscountPercent(), p.getCurrency(),
                stats != null && stats.getAverageRating() != null ? stats.getAverageRating().doubleValue() : null,
                stats != null ? stats.getReviewCount() : null,
                p.getStock(), p.getBadge(), p.getDescription(), images,
                p.getStock() != null && p.getStock() >= 10,
                p.getBulkMinQty(), p.getBulkContactPhone(),
                p.getWeight(), p.getDimensions(), p.getMaterial(),
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}
