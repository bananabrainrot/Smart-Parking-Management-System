package components.reporting;

public class ReportingComponent {
    private final ReportingService reportingService;

    public ReportingComponent(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    public ReportingService reportingService() {
        return reportingService;
    }
}
