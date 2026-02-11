package components.dataaccess.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public interface ReportingGateway {
    BigDecimal getRevenueBetween(LocalDateTime start, LocalDateTime end);
    Map<Integer, Long> getPopularStartHours(LocalDateTime start, LocalDateTime end);
}
