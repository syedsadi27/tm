package com.tm.trophiesmementoes.backend.search;

import com.tm.trophiesmementoes.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/suggestions")
    public ApiResponse<List<SuggestionDto>> getSuggestions(
            @RequestParam String q,
            @RequestParam(defaultValue = "6") int limit) {
        return ApiResponse.ok(searchService.getSuggestions(q, limit));
    }
}
