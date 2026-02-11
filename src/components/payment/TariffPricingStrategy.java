package components.payment;

import entities.Tariff;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TariffPricingStrategy {
    boolean supports(Tariff tariff);
    BigDecimal calculate(LocalDateTime start, LocalDateTime end, Tariff tariff);
}
