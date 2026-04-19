package com.tm.trophiesmementoes.backend.review;

import com.tm.trophiesmementoes.backend.common.Meta;
import com.tm.trophiesmementoes.backend.product.ProductStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductStatsRepository statsRepository;

    public record ReviewPage(List<ReviewDto> reviews, Meta meta) {}

    public ReviewPage getReviews(String productId, int page, int pageSize, String sortBy, String sortOrder) {
        Sort.Direction dir = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        String field = "rating".equals(sortBy) ? "rating" : "createdAt";
        PageRequest pageable = PageRequest.of(page - 1, pageSize, Sort.by(dir, field));

        Page<Review> result = reviewRepository.findByProductId(productId, pageable);
        List<ReviewDto> dtos = result.getContent().stream().map(ReviewDto::from).toList();

        Double avgRating = statsRepository.findByProductId(productId)
                .map(s -> s.getAverageRating() != null ? s.getAverageRating().doubleValue() : null)
                .orElse(null);

        Map<String, Long> breakdown = buildBreakdown(productId);
        Meta meta = new Meta(result).withRatingData(avgRating, breakdown);

        return new ReviewPage(dtos, meta);
    }

    private Map<String, Long> buildBreakdown(String productId) {
        Map<String, Long> map = new LinkedHashMap<>();
        for (int i = 5; i >= 1; i--) map.put(String.valueOf(i), 0L);
        reviewRepository.getRatingBreakdown(productId)
                .forEach(row -> map.put(String.valueOf(row[0]), (Long) row[1]));
        return map;
    }
}
