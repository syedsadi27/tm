package com.tm.trophiesmementoes.backend.delivery;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryCheckDto(
        String pincode,
        Boolean deliverable,
        String estimatedDays,
        LocalDate estimatedDeliveryDate,
        BigDecimal shippingCharge,
        Boolean codAvailable,
        String message
) {}
