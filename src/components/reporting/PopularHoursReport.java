package components.reporting;

import java.time.LocalDateTime;
import java.util.Map;

public class PopularHoursReport {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final Map<Integer, Long> reservationsByHour;

    public PopularHoursReport(LocalDateTime from, LocalDateTime to, Map<Integer, Long> reservationsByHour) {
        this.from = from;
        this.to = to;
        this.reservationsByHour = reservationsByHour;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public Map<Integer, Long> getReservationsByHour() {
        return reservationsByHour;
    }
}
