package components.monitoring;

public class MonitoringComponent {
    private final MonitoringService monitoringService;

    public MonitoringComponent(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    public MonitoringService monitoringService() {
        return monitoringService;
    }
}
