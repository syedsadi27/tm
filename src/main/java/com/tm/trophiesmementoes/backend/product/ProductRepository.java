package com.tm.trophiesmementoes.backend.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findBySlug(String slug);

    @Query("""
        SELECT p FROM Product p
        LEFT JOIN FETCH p.category
        WHERE (:categorySlug IS NULL OR p.category.slug = :categorySlug)
          AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
                               OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:badge IS NULL OR p.badge = :badge)
          AND (:inStock IS NULL OR (:inStock = true AND p.stock > 0))
        """)
    Page<Product> findWithFilters(
            @Param("categorySlug") String categorySlug,
            @Param("search") String search,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("badge") String badge,
            @Param("inStock") Boolean inStock,
            Pageable pageable);

    @Query(value = "SELECT TOP (:limit) * FROM products WHERE badge IS NOT NULL ORDER BY created_at DESC", nativeQuery = true)
    List<Product> findFeatured(@Param("limit") int limit);

    @Query(value = "SELECT TOP (:limit) * FROM products ORDER BY created_at DESC", nativeQuery = true)
    List<Product> findNewArrivals(@Param("limit") int limit);

    @Query(value = "SELECT TOP (:limit) * FROM products WHERE price <= :maxPrice ORDER BY price ASC", nativeQuery = true)
    List<Product> findBudget(@Param("maxPrice") BigDecimal maxPrice, @Param("limit") int limit);

    @Query(value = """
        SELECT TOP (:limit) * FROM products
        WHERE category_id = (SELECT category_id FROM products WHERE id = :productId)
          AND id != :productId
        """, nativeQuery = true)
    List<Product> findSimilar(@Param("productId") String productId, @Param("limit") int limit);

    @Query("""
        SELECT p FROM Product p
        WHERE p.category.id IN (
            SELECT DISTINCT p2.category.id FROM Product p2 WHERE p2.id IN :viewedIds
        )
        AND p.id NOT IN :viewedIds
        """)
    List<Product> findRecommendations(@Param("viewedIds") List<String> viewedIds, Pageable pageable);
}
