package components.payment;

import entities.Tariff;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PricingService {
    private final List<TariffPricingStrategy> strategies;

    public PricingService(List<TariffPricingStrategy> strategies) {
        this.strategies = strategies;
    }

    public BigDecimal calculateCost(LocalDateTime start, LocalDateTime end, Tariff tariff) {
        TariffPricingStrategy strategy = strategies.stream()
                .filter(s -> s.supports(tariff))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No strategy for tariff: " + tariff.getName()));
        return strategy.calculate(start, end, tariff);
    }
}
