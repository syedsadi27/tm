package com.tm.trophiesmementoes.backend.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findBySlug(String slug);

    List<Category> findAllByOrderBySortOrderAsc();

    @Query(value = "SELECT TOP (:limit) * FROM categories ORDER BY sort_order ASC", nativeQuery = true)
    List<Category> findTopByOrderBySortOrder(int limit);
}
