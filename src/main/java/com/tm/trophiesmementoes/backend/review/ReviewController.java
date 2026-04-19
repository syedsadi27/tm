package com.tm.trophiesmementoes.backend.review;

import com.tm.trophiesmementoes.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{id}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ApiResponse<List<ReviewDto>> getReviews(
            @PathVariable String id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        ReviewService.ReviewPage result = reviewService.getReviews(id, page, pageSize, sortBy, sortOrder);
        return ApiResponse.ok(result.reviews(), result.meta());
    }
}
