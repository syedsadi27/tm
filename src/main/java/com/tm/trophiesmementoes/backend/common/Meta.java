package com.tm.trophiesmementoes.backend.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {

    private final int page;
    private final int pageSize;
    private final long totalItems;
    private final int totalPages;
    private final boolean hasNextPage;
    private final boolean hasPrevPage;
    private Double averageRating;
    private java.util.Map<String, Long> ratingBreakdown;

    public Meta(Page<?> page) {
        this.page = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.hasNextPage = page.hasNext();
        this.hasPrevPage = page.hasPrevious();
    }

    public Meta withRatingData(Double averageRating, java.util.Map<String, Long> ratingBreakdown) {
        this.averageRating = averageRating;
        this.ratingBreakdown = ratingBreakdown;
        return this;
    }
}
