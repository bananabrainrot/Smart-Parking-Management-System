package services;

import entities.*;
import exception.*;
import repositories.*;
import java.time.LocalDateTime;

public class ReservationService {
    private final IReservationRepository reservationRepo;
    private final IParkingSpotRepository spotRepo;
    private final IVehicleRepository vehicleRepo;

    public ReservationService(IReservationRepository rr, IParkingSpotRepository sr, IVehicleRepository vr) {
        this.reservationRepo = rr;
        this.spotRepo = sr;
        this.vehicleRepo = vr;
    }

    public void processReservation(String plate, int spotId, int tariffId) {
        if (plate == null || plate.length() < 5) throw new InvalidVehiclePlateException("Bad plate!");

        ParkingSpot spot = spotRepo.getById(spotId);
        if (spot == null || !spot.getAvailable()) throw new NoFreeSpotsException("Spot is busy!");

        Vehicle vehicle = vehicleRepo.findByPlate(plate);
        if (vehicle == null) {
            vehicle = new Vehicle(0, plate, "Unknown", "Car");
            vehicleRepo.save(vehicle);
        }

        Reservation res = new Reservation(0, spotId, vehicle.getId(), tariffId, LocalDateTime.now());
        reservationRepo.create(res);

        spotRepo.updateSpotStatus(spotId, false);
    }
    private final PricingService pricingService = new PricingService();

    public void releaseSpot(String plate) {
        Vehicle vehicle = vehicleRepo.findByPlate(plate);
        if (vehicle == null) throw new InvalidVehiclePlateException("Vehicle not found");

        Reservation res = reservationRepo.findActiveByVehicleId(vehicle.getId());
        if (res == null) throw new
                ReservationNotFoundException("No active reservation for this car");

        java.time.LocalDateTime endTime = java.time.LocalDateTime.now();
        java.math.BigDecimal rate = java.math.BigDecimal.valueOf(150.00);
        java.math.BigDecimal totalCost = pricingService.calculateCost(
                res.getStartTime(),
                endTime,
                rate
        );

        reservationRepo.finishReservation(res.getId(), totalCost);
        spotRepo.updateSpotStatus(res.getSpotId(), true);

        Invoice invoice = new Invoice.Builder()
                .plate(plate)
                .spotId(res.getSpotId())
                .startTime(res.getStartTime())
                .endTime(endTime)
                .totalCost(totalCost)
                .build();

        System.out.println("Spot released! Total cost: " + invoice.getTotalCost());
    }
}
