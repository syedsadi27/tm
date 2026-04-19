package com.tm.trophiesmementoes.backend.delivery;

import com.tm.trophiesmementoes.backend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/check")
    public ApiResponse<DeliveryCheckDto> check(
            @RequestParam String pincode,
            @RequestParam(required = false) String productId) {
        return ApiResponse.ok(deliveryService.check(pincode));
    }
}
