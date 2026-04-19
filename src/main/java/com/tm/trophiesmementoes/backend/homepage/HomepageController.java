package com.tm.trophiesmementoes.backend.homepage;

import com.tm.trophiesmementoes.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homepage")
@RequiredArgsConstructor
public class HomepageController {

    private final HomepageService homepageService;

    @GetMapping
    public ApiResponse<HomepageDto> getHomepage() {
        return ApiResponse.ok(homepageService.getHomepage());
    }
}
