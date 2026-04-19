package com.tm.trophiesmementoes.backend.product;

import com.tm.trophiesmementoes.backend.common.InvalidParamsException;
import com.tm.trophiesmementoes.backend.common.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final ProductStatsRepository statsRepository;
    private final ProductMapper mapper;

    public Page<ProductDto.ListItem> getProducts(
            int page, int pageSize, String category, String search,
            BigDecimal minPrice, BigDecimal maxPrice,
            String sortBy, String sortOrder, String badge, Boolean inStock) {

        pageSize = Math.min(pageSize, 100);
        Sort sort = buildSort(sortBy, sortOrder);
        PageRequest pageable = PageRequest.of(page - 1, pageSize, sort);

        Page<Product> products = productRepository.findWithFilters(
                category, search, minPrice, maxPrice, badge, inStock, pageable);

        return products.map(p -> toListItem(p));
    }

    public ProductDto.Detail getById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return toDetail(product);
    }

    public List<ProductDto.ListItem> getFeatured(int limit) {
        return productRepository.findFeatured(limit).stream().map(this::toListItem).toList();
    }

    public List<ProductDto.ListItem> getNewArrivals(int limit) {
        return productRepository.findNewArrivals(limit).stream().map(this::toListItem).toList();
    }

    public List<ProductDto.ListItem> getBudget(BigDecimal maxPrice, int limit) {
        return productRepository.findBudget(maxPrice, limit).stream().map(this::toListItem).toList();
    }

    public List<ProductDto.ListItem> getSimilar(String productId, int limit) {
        if (!productRepository.existsById(productId))
            throw new ResourceNotFoundException("Product not found: " + productId);
        return productRepository.findSimilar(productId, limit).stream().map(this::toListItem).toList();
    }

    public List<ProductDto.ListItem> getRecommendations(List<String> viewedIds, int limit) {
        if (viewedIds.size() < 3)
            throw new InvalidParamsException("At least 3 viewedIds are required");
        PageRequest pageable = PageRequest.of(0, limit);
        return productRepository.findRecommendations(viewedIds, pageable).stream().map(this::toListItem).toList();
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    ProductDto.ListItem toListItem(Product p) {
        ProductStats stats = statsRepository.findByProductId(p.getId()).orElse(null);
        List<ProductImage> images = imageRepository.findByProductIdOrderBySortOrderAsc(p.getId());
        String thumbnail = images.stream().filter(i -> Boolean.TRUE.equals(i.getIsThumbnail()))
                .map(ProductImage::getImageUrl).findFirst()
                .orElse(images.isEmpty() ? null : images.get(0).getImageUrl());
        List<String> imageUrls = images.stream().map(ProductImage::getImageUrl).toList();
        return mapper.toListItem(p, stats, thumbnail, imageUrls);
    }

    private ProductDto.Detail toDetail(Product p) {
        ProductStats stats = statsRepository.findByProductId(p.getId()).orElse(null);
        List<String> imageUrls = imageRepository.findByProductIdOrderBySortOrderAsc(p.getId())
                .stream().map(ProductImage::getImageUrl).toList();
        return mapper.toDetail(p, stats, imageUrls);
    }

    private Sort buildSort(String sortBy, String sortOrder) {
        Sort.Direction dir = "desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return switch (sortBy == null ? "featured" : sortBy) {
            case "price"   -> Sort.by(dir, "price");
            case "rating"  -> Sort.by(dir, "id"); // joined via stats; default fallback
            case "newest"  -> Sort.by(Sort.Direction.DESC, "createdAt");
            default        -> Sort.by(Sort.Direction.DESC, "createdAt"); // featured
        };
    }
}
