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
        // 1. Проверка номера (Exception: invalid vehicle plate)
        if (plate == null || plate.length() < 5) throw new InvalidVehiclePlateException("Bad plate!");

        // 2. Проверка места (Exception: no free spots)
        ParkingSpot spot = spotRepo.getById(spotId);
        if (spot == null || !spot.getAvailable()) throw new NoFreeSpotsException("Spot is busy!");

        // 3. Ищем или создаем машину
        Vehicle vehicle = vehicleRepo.findByPlate(plate);
        if (vehicle == null) {
            vehicle = new Vehicle(0, plate, "Unknown", "Car");
            vehicleRepo.save(vehicle);
        }

        // 4. Создаем бронь
        Reservation res = new Reservation(0, spotId, vehicle.getId(), tariffId, LocalDateTime.now());
        reservationRepo.create(res);

        // 5. Помечаем место как занятое
        spotRepo.updateSpotStatus(spotId, false);
    }
    // Добавь это в ReservationService
    private final PricingService pricingService = new PricingService();

    public void releaseSpot(String plate) {
        // 1. Ищем машину
        Vehicle vehicle = vehicleRepo.findByPlate(plate);
        if (vehicle == null) throw new InvalidVehiclePlateException("Vehicle not found");

        // 2. Ищем активную бронь
        Reservation res = reservationRepo.findActiveByVehicleId(vehicle.getId());
        if (res == null) throw new ReservationNotFoundException("No active reservation for this car");

        // 3. Считаем стоимость (нужно достать тариф из базы или использовать дефолт)
        // Для простоты Milestone 1 предположим, что тариф 150/час
        java.math.BigDecimal rate = java.math.BigDecimal.valueOf(150.00);
        java.math.BigDecimal totalCost = pricingService.calculateCost(
                res.getStartTime(),
                java.time.LocalDateTime.now(),
                rate
        );

        // 4. Завершаем в БД
        reservationRepo.finishReservation(res.getId(), totalCost);
        spotRepo.updateSpotStatus(res.getSpotId(), true);

        System.out.println("Spot released! Total cost: " + totalCost);
    }
}