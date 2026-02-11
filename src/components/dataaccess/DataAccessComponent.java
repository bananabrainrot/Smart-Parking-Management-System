package components.dataaccess;

import components.dataaccess.api.*;
import components.dataaccess.postgres.*;

public class DataAccessComponent {
    private final ParkingSpotGateway parkingSpotGateway;
    private final VehicleGateway vehicleGateway;
    private final ReservationGateway reservationGateway;
    private final TariffGateway tariffGateway;
    private final ReportingGateway reportingGateway;

    public DataAccessComponent() {
        this.parkingSpotGateway = new PostgresParkingSpotGateway();
        this.vehicleGateway = new PostgresVehicleGateway();
        this.reservationGateway = new PostgresReservationGateway();
        this.tariffGateway = new PostgresTariffGateway();
        this.reportingGateway = new PostgresReportingGateway();
    }

    public ParkingSpotGateway parkingSpots() { return parkingSpotGateway; }
    public VehicleGateway vehicles() { return vehicleGateway; }
    public ReservationGateway reservations() { return reservationGateway; }
    public TariffGateway tariffs() { return tariffGateway; }
    public ReportingGateway reporting() { return reportingGateway; }
}
