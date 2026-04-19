package com.tm.trophiesmementoes.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStatsRepository extends JpaRepository<ProductStats, String> {
    Optional<ProductStats> findByProductId(String productId);
}
