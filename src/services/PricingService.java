package services;

import java.time.Duration;
import java.time.LocalDateTime;

public class PricingService {

    /**
     * Рассчитывает стоимость на основе времени и тарифа.
     * Округляет время в большую сторону до полного часа.
     */
    public double calculateCost(LocalDateTime start, LocalDateTime end, double ratePerHour) {
        if (start == null || end == null) return 0.0;

        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();

        if (minutes <= 0) return 0.0;

        // Пример: 61 минута считается как 2 часа
        double hours = Math.ceil(minutes / 60.0);

        return hours * ratePerHour;
    }
}