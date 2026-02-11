package components.payment;

import entities.Tariff;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyTariffPricingStrategy implements TariffPricingStrategy {
    @Override
    public boolean supports(Tariff tariff) {
        return true;
    }

    @Override
    public BigDecimal calculate(LocalDateTime start, LocalDateTime end, Tariff tariff) {
        long hours = Duration.between(start, end).toHours();
        if (hours < 1) {
            hours = 1;
        }
        return tariff.getRatePerHour().multiply(BigDecimal.valueOf(hours));
    }
}
