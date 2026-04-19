package com.tm.trophiesmementoes.backend.category;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDto(
        String id,
        String slug,
        String label,
        String description,
        String imageUrl,
        String bannerUrl,
        Long count,
        String color,
        Integer sortOrder
) {
    // List view (no description/bannerUrl)
    static CategoryDto forList(Category c, Long count) {
        return new CategoryDto(c.getId(), c.getSlug(), c.getLabel(), null,
                c.getImageUrl(), null, count, c.getColor(), c.getSortOrder());
    }

    // Detail view (all fields)
    static CategoryDto forDetail(Category c, Long count) {
        return new CategoryDto(c.getId(), c.getSlug(), c.getLabel(), c.getDescription(),
                c.getImageUrl(), c.getBannerUrl(), count, c.getColor(), c.getSortOrder());
    }
}
