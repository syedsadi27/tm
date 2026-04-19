package com.tm.trophiesmementoes.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductIdOrderBySortOrderAsc(String productId);
    Optional<ProductImage> findByProductIdAndIsThumbnailTrue(String productId);
}
