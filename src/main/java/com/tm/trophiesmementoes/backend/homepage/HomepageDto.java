package com.tm.trophiesmementoes.backend.homepage;

import com.tm.trophiesmementoes.backend.category.CategoryDto;
import com.tm.trophiesmementoes.backend.product.ProductDto;

import java.time.Instant;
import java.util.List;

public record HomepageDto(
        List<CategoryDto> categories,
        List<ProductDto.ListItem> featuredProducts,
        List<ProductDto.ListItem> newArrivals,
        List<ProductDto.ListItem> budgetProducts
) {}
