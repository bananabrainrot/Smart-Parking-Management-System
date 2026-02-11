package components.monitoring;

import components.dataaccess.api.ParkingSpotGateway;
import components.dataaccess.api.ReservationGateway;

public class MonitoringService {
    private final ParkingSpotGateway parkingSpotGateway;
    private final ReservationGateway reservationGateway;

    public MonitoringService(ParkingSpotGateway parkingSpotGateway, ReservationGateway reservationGateway) {
        this.parkingSpotGateway = parkingSpotGateway;
        this.reservationGateway = reservationGateway;
    }

    public ParkingStatus getCurrentStatus() {
        int freeSpots = parkingSpotGateway.getAllFreeSpots().getTotalCount();
        int activeReservations = reservationGateway.getActiveReservationsCount();
        return new ParkingStatus(freeSpots, activeReservations);
    }
}
