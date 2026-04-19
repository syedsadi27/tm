package com.tm.trophiesmementoes.backend.homepage;

import com.tm.trophiesmementoes.backend.category.CategoryDto;
import com.tm.trophiesmementoes.backend.category.CategoryService;
import com.tm.trophiesmementoes.backend.product.ProductDto;
import com.tm.trophiesmementoes.backend.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageService {

    private final CategoryService categoryService;
    private final ProductService productService;

    public HomepageDto getHomepage() {
        List<CategoryDto> categories = categoryService.getAll(true, 6);
        List<ProductDto.ListItem> featured = productService.getFeatured(8);
        List<ProductDto.ListItem> newArrivals = productService.getNewArrivals(20);
        List<ProductDto.ListItem> budget = productService.getBudget(new BigDecimal("80"), 4);

        return new HomepageDto(categories, featured, newArrivals, budget);
    }
}
