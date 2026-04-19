package com.tm.trophiesmementoes.backend.delivery;

import com.tm.trophiesmementoes.backend.common.InvalidParamsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final PincodeRepository pincodeRepository;

    public DeliveryCheckDto check(String pincode) {
        if (pincode == null || pincode.isBlank())
            throw new InvalidParamsException("pincode is required");

        Optional<Pincode> opt = pincodeRepository.findById(pincode.trim());

        if (opt.isEmpty() || Boolean.FALSE.equals(opt.get().getDeliverable())) {
            return new DeliveryCheckDto(pincode, false, null, null, null, null,
                    "Sorry, delivery not available to this pincode yet.");
        }

        Pincode p = opt.get();
        LocalDate estimatedDate = estimateDeliveryDate(p.getEstimatedDays());
        String message = "Delivery available — estimated " + p.getEstimatedDays() + " business days";

        return new DeliveryCheckDto(pincode, true, p.getEstimatedDays(),
                estimatedDate, p.getShippingCharge(), p.getCodAvailable(), message);
    }

    private LocalDate estimateDeliveryDate(String estimatedDays) {
        if (estimatedDays == null) return null;
        try {
            // handles "3-5" format — use upper bound
            String[] parts = estimatedDays.split("-");
            int days = Integer.parseInt(parts[parts.length - 1].trim());
            return LocalDate.now().plusDays(days);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
