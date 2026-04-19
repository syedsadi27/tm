package com.tm.trophiesmementoes.backend.search;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SuggestionDto(
        String type,
        String label,
        String id,
        String slug
) {
    static SuggestionDto product(String id, String label, String slug) {
        return new SuggestionDto("product", label, id, slug);
    }

    static SuggestionDto category(String label, String slug) {
        return new SuggestionDto("category", label, null, slug);
    }
}
