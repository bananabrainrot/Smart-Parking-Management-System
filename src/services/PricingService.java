package services;
import java.time.Duration;
import java.time.LocalDateTime;

public class PricingService {
    public double calculateCost(LocalDateTime start, LocalDateTime end, double ratePerHour) {
        if (start == null || end == null) return 0.0;
        long minutes = Duration.between(start, end).toMinutes();
        if (minutes <= 0) return 0.0;

        double hours = Math.ceil(minutes / 60.0);
        return hours * ratePerHour;
    }
}