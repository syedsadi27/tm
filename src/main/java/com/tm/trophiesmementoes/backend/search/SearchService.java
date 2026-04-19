package com.tm.trophiesmementoes.backend.search;

import com.tm.trophiesmementoes.backend.category.CategoryRepository;
import com.tm.trophiesmementoes.backend.common.InvalidParamsException;
import com.tm.trophiesmementoes.backend.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<SuggestionDto> getSuggestions(String q, int limit) {
        if (q == null || q.trim().length() < 2)
            throw new InvalidParamsException("Query must be at least 2 characters");

        String term = q.trim();
        List<SuggestionDto> suggestions = new ArrayList<>();

        // product matches
        productRepository.findWithFilters(null, term, null, null, null, null,
                        PageRequest.of(0, limit))
                .forEach(p -> suggestions.add(SuggestionDto.product(p.getId(), p.getName(), p.getSlug())));

        // category matches (fill remaining slots)
        int remaining = limit - suggestions.size();
        if (remaining > 0) {
            categoryRepository.findAllByOrderBySortOrderAsc().stream()
                    .filter(c -> c.getLabel().toLowerCase().contains(term.toLowerCase()))
                    .limit(remaining)
                    .forEach(c -> suggestions.add(SuggestionDto.category(c.getLabel(), c.getSlug())));
        }

        return suggestions;
    }
}
