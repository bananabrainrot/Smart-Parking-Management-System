package components.payment;

import entities.Tariff;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class EarlyBirdTariffPricingStrategy implements TariffPricingStrategy {
    @Override
    public boolean supports(Tariff tariff) {
        return "EarlyBird".equalsIgnoreCase(tariff.getName());
    }

    @Override
    public BigDecimal calculate(LocalDateTime start, LocalDateTime end, Tariff tariff) {
        long hours = Duration.between(start, end).toHours();
        if (hours <= 8) {
            return tariff.getRatePerHour();
        }
        return tariff.getRatePerHour().add(
                tariff.getRatePerHour().multiply(BigDecimal.valueOf(hours - 8))
        );
    }
}
