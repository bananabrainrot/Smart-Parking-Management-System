package services;

import repositories.IParkingSpotRepository;
import repositories.IReservationRepository;
import repositories.IVehicleRepository;

public class ParkingLotManager {
    private static ParkingLotManager instance;
    private final ReservationService reservationService;

    private ParkingLotManager(IReservationRepository reservationRepo,
                              IParkingSpotRepository spotRepo,
                              IVehicleRepository vehicleRepo) {
        this.reservationService = new ReservationService(reservationRepo, spotRepo, vehicleRepo);
    }

    public static synchronized ParkingLotManager getInstance(IReservationRepository reservationRepo,
                                                             IParkingSpotRepository spotRepo,
                                                             IVehicleRepository vehicleRepo) {
        if (instance == null) {
            instance = new ParkingLotManager(reservationRepo, spotRepo, vehicleRepo);
        }
        return instance;
    }

    public void processReservation(String plate, int spotId, int tariffId) {
        reservationService.processReservation(plate, spotId, tariffId);
    }

    public void releaseSpot(String plate) {
        reservationService.releaseSpot(plate);
    }
}
