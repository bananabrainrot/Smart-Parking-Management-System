package components.reporting;

import components.dataaccess.api.ReportingGateway;

import java.time.LocalDateTime;

public class ReportingService {
    private final ReportingGateway reportingGateway;

    public ReportingService(ReportingGateway reportingGateway) {
        this.reportingGateway = reportingGateway;
    }

    public RevenueReport generateRevenueReport(LocalDateTime from, LocalDateTime to) {
        return new RevenueReport(from, to, reportingGateway.getRevenueBetween(from, to));
    }

    public PopularHoursReport generatePopularHoursReport(LocalDateTime from, LocalDateTime to) {
        return new PopularHoursReport(from, to, reportingGateway.getPopularStartHours(from, to));
    }
}
