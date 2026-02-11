package components;

import config.ParkingPolicy;
import config.TariffPlan;

public class ReportingComponent {
    private final MonitoringComponent monitoringComponent;
    private final ParkingPolicy parkingPolicy;

    public ReportingComponent(MonitoringComponent monitoringComponent, ParkingPolicy parkingPolicy) {
        this.monitoringComponent = monitoringComponent;
        this.parkingPolicy = parkingPolicy;
    }

    public void printOperationalReport() {
        int freeSpots = monitoringComponent.showFreeSpots().getTotalCount();

        System.out.println("\n--- REPORT ---");
        System.out.println("Free spots available: " + freeSpots);
        System.out.println("Active tariffs:");
        for (TariffPlan tariffPlan : parkingPolicy.getTariffPlans()) {
            System.out.println("- #" + tariffPlan.getId() + " " + tariffPlan.getName() + " = "
                    + tariffPlan.getRatePerHour() + " per hour");
        }
    }
}
