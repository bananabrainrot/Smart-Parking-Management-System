package services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class PricingService {
    public BigDecimal calculateCost(LocalDateTime start, LocalDateTime end, BigDecimal ratePerHour) {
        long hours = Duration.between(start, end).toHours();
        if (hours < 1) hours = 1;
        return ratePerHour.multiply(BigDecimal.valueOf(hours));
    }
}