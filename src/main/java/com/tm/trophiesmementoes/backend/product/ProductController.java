package com.tm.trophiesmementoes.backend.product;

import com.tm.trophiesmementoes.backend.common.ApiResponse;
import com.tm.trophiesmementoes.backend.common.Meta;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductDto.ListItem>> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int pageSize,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "featured") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String badge,
            @RequestParam(required = false) Boolean inStock) {

        Page<ProductDto.ListItem> result = productService.getProducts(
                page, pageSize, category, search, minPrice, maxPrice, sortBy, sortOrder, badge, inStock);
        return ApiResponse.ok(result.getContent(), new Meta(result));
    }

    @GetMapping("/featured")
    public ApiResponse<List<ProductDto.ListItem>> getFeatured(@RequestParam(defaultValue = "8") int limit) {
        return ApiResponse.ok(productService.getFeatured(limit));
    }

    @GetMapping("/new-arrivals")
    public ApiResponse<List<ProductDto.ListItem>> getNewArrivals(@RequestParam(defaultValue = "20") int limit) {
        return ApiResponse.ok(productService.getNewArrivals(limit));
    }

    @GetMapping("/budget")
    public ApiResponse<List<ProductDto.ListItem>> getBudget(
            @RequestParam(defaultValue = "80") BigDecimal maxPrice,
            @RequestParam(defaultValue = "4") int limit) {
        return ApiResponse.ok(productService.getBudget(maxPrice, limit));
    }

    @GetMapping("/similar")
    public ApiResponse<List<ProductDto.ListItem>> getSimilar(
            @RequestParam String productId,
            @RequestParam(defaultValue = "4") int limit) {
        return ApiResponse.ok(productService.getSimilar(productId, limit));
    }

    @GetMapping("/recommendations")
    public ApiResponse<List<ProductDto.ListItem>> getRecommendations(
            @RequestParam String viewedIds,
            @RequestParam(defaultValue = "8") int limit) {
        List<String> ids = Arrays.asList(viewedIds.split(","));
        return ApiResponse.ok(productService.getRecommendations(ids, limit));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDto.Detail> getById(@PathVariable String id) {
        return ApiResponse.ok(productService.getById(id));
    }
}
