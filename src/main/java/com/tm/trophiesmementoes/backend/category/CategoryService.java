package com.tm.trophiesmementoes.backend.category;

import com.tm.trophiesmementoes.backend.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryProductCountRepository countRepository;

    public List<CategoryDto> getAll(boolean includeCount, Integer limit) {
        List<Category> categories = limit != null
                ? categoryRepository.findTopByOrderBySortOrder(limit)
                : categoryRepository.findAllByOrderBySortOrderAsc();

        Map<String, Long> counts = includeCount ? countRepository.getProductCountsMap() : Map.of();

        return categories.stream()
                .map(c -> CategoryDto.forList(c, includeCount ? counts.getOrDefault(c.getId(), 0L) : null))
                .toList();
    }

    public CategoryDto getBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + slug));
        Long count = countRepository.countByCategory(category.getId());
        return CategoryDto.forDetail(category, count);
    }
}
