package components;

import config.ParkingPolicy;
import repositories.IParkingSpotRepository;
import repositories.IReservationRepository;
import repositories.IVehicleRepository;
import repositories.implementations.PostgresParkingSpotRepository;
import repositories.implementations.PostgresReservationRepository;
import repositories.implementations.PostgresVehicleRepository;

public class DataAccessComponent {
    private final IReservationRepository reservationRepository;
    private final IParkingSpotRepository parkingSpotRepository;
    private final IVehicleRepository vehicleRepository;

    public DataAccessComponent(ParkingPolicy parkingPolicy) {
        this.reservationRepository = new PostgresReservationRepository();
        this.parkingSpotRepository = new PostgresParkingSpotRepository(parkingPolicy);
        this.vehicleRepository = new PostgresVehicleRepository();
    }

    public IReservationRepository reservations() {
        return reservationRepository;
    }

    public IParkingSpotRepository parkingSpots() {
        return parkingSpotRepository;
    }

    public IVehicleRepository vehicles() {
        return vehicleRepository;
    }
}
