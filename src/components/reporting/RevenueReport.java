package components.reporting;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RevenueReport {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final BigDecimal totalRevenue;

    public RevenueReport(LocalDateTime from, LocalDateTime to, BigDecimal totalRevenue) {
        this.from = from;
        this.to = to;
        this.totalRevenue = totalRevenue;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
}
