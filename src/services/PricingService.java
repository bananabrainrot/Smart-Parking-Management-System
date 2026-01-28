package services;

import java.time.Duration;
import java.time.LocalDateTime;

public class PricingService {
    public double calculate(LocalDateTime start, LocalDateTime end, double ratePerHour) {
        if (end == null) end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();

        // Округляем до часов в большую сторону (например)
        double hours = Math.ceil(minutes / 60.0);
        return hours * ratePerHour;
    }
}
