package com.tm.trophiesmementoes.backend.category;

import com.tm.trophiesmementoes.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<CategoryDto>> getAll(
            @RequestParam(defaultValue = "true") boolean includeCount,
            @RequestParam(required = false) Integer limit) {
        return ApiResponse.ok(categoryService.getAll(includeCount, limit));
    }

    @GetMapping("/{slug}")
    public ApiResponse<CategoryDto> getBySlug(@PathVariable String slug) {
        return ApiResponse.ok(categoryService.getBySlug(slug));
    }
}
