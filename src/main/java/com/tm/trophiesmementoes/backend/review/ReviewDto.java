package com.tm.trophiesmementoes.backend.review;

import java.time.Instant;

public record ReviewDto(
        String id,
        String productId,
        String authorName,
        String authorInitials,
        Integer rating,
        String title,
        String text,
        Instant date,
        Boolean verified
) {
    static ReviewDto from(Review r) {
        return new ReviewDto(r.getId(), r.getProductId(), r.getAuthorName(), r.getAuthorInitials(),
                r.getRating(), r.getTitle(), r.getText(), r.getCreatedAt(), r.getVerified());
    }
}
