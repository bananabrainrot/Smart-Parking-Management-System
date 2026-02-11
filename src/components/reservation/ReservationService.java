package components.reservation;

import components.dataaccess.api.ParkingSpotGateway;
import components.dataaccess.api.ReservationGateway;
import components.dataaccess.api.VehicleGateway;
import components.payment.PaymentService;
import entities.Invoice;
import entities.ParkingSpot;
import entities.Reservation;
import entities.Vehicle;
import exception.InvalidVehiclePlateException;
import exception.NoFreeSpotsException;
import exception.ReservationNotFoundException;

import java.time.LocalDateTime;

public class ReservationService {
    private final ReservationGateway reservationGateway;
    private final ParkingSpotGateway parkingSpotGateway;
    private final VehicleGateway vehicleGateway;
    private final PaymentService paymentService;

    public ReservationService(ReservationGateway reservationGateway,
                              ParkingSpotGateway parkingSpotGateway,
                              VehicleGateway vehicleGateway,
                              PaymentService paymentService) {
        this.reservationGateway = reservationGateway;
        this.parkingSpotGateway = parkingSpotGateway;
        this.vehicleGateway = vehicleGateway;
        this.paymentService = paymentService;
    }

    public void processReservation(String plate, int spotId, int tariffId) {
        if (plate == null || plate.length() < 5) {
            throw new InvalidVehiclePlateException("Bad plate!");
        }

        ParkingSpot spot = parkingSpotGateway.findById(spotId);
        if (spot == null || !spot.getAvailable()) {
            throw new NoFreeSpotsException("Spot is busy!");
        }

        Vehicle vehicle = vehicleGateway.findByPlate(plate);
        if (vehicle == null) {
            vehicle = new Vehicle(0, plate, "Unknown", "Car");
            vehicleGateway.save(vehicle);
        }

        Reservation reservation = new Reservation(0, spotId, vehicle.getId(), tariffId, LocalDateTime.now());
        reservationGateway.create(reservation);
        parkingSpotGateway.updateAvailability(spotId, false);
    }

    public Invoice releaseSpot(String plate) {
        Vehicle vehicle = vehicleGateway.findByPlate(plate);
        if (vehicle == null) {
            throw new InvalidVehiclePlateException("Vehicle not found");
        }

        Reservation reservation = reservationGateway.findActiveByVehicleId(vehicle.getId())
                .orElseThrow(() -> new ReservationNotFoundException("No active reservation for this car"));

        Invoice invoice = paymentService.closeReservationAndCreateInvoice(plate, reservation);
        parkingSpotGateway.updateAvailability(reservation.getSpotId(), true);
        return invoice;
    }
}
