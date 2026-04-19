package com.tm.trophiesmementoes.backend.delivery;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pincodes")
@Getter @Setter
public class Pincode {

    @Id
    private String pincode;

    private Boolean deliverable = true;

    @Column(name = "estimated_days")
    private String estimatedDays;

    @Column(name = "shipping_charge", precision = 10, scale = 2)
    private BigDecimal shippingCharge = BigDecimal.ZERO;

    @Column(name = "cod_available")
    private Boolean codAvailable = true;
}
