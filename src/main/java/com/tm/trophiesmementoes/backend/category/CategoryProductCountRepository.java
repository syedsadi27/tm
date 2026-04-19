package com.tm.trophiesmementoes.backend.category;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryProductCountRepository {

    private final EntityManager em;

    public Map<String, Long> getProductCountsMap() {
        List<Object[]> rows = em.createQuery(
                "SELECT p.category.id, COUNT(p) FROM Product p GROUP BY p.category.id", Object[].class
        ).getResultList();
        return rows.stream().collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));
    }

    public Long countByCategory(String categoryId) {
        return em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.category.id = :cid", Long.class)
                .setParameter("cid", categoryId)
                .getSingleResult();
    }
}
