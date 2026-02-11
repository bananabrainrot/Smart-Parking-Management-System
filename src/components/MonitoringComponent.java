package components;

import entities.ListResult;
import entities.ParkingSpot;

public class MonitoringComponent {
    private final DataAccessComponent dataAccessComponent;

    public MonitoringComponent(DataAccessComponent dataAccessComponent) {
        this.dataAccessComponent = dataAccessComponent;
    }

    public ListResult<ParkingSpot> showFreeSpots() {
        return dataAccessComponent.parkingSpots().getAllFreeSpots();
    }
}
