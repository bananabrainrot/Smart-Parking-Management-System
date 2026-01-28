package services;

import entities.*;
import exception.*;
import repositories.*;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {
    private final IParkingSpotRepository spotRepo;
    private final IVehicleRepository vehicleRepo;
    private final IReservationRepository reservationRepo;
    private final ITariffRepository tariffRepo;
    private final PricingService pricingService;

    // SOLID: Внедрение зависимостей через конструктор
    public ReservationService(IParkingSpotRepository spotRepo,
                              IVehicleRepository vehicleRepo,
                              IReservationRepository reservationRepo,
                              ITariffRepository tariffRepo,
                              PricingService pricingService) {
        this.spotRepo = spotRepo;
        this.vehicleRepo = vehicleRepo;
        this.reservationRepo = reservationRepo;
        this.tariffRepo = tariffRepo;
        this.pricingService = pricingService;
    }

    /**
     * User Story: Reserve a spot
     */
    public void reserve(String plateNumber) throws NoFreeSpotsException, InvalidVehiclePlateException {
        // 1. Простейшая валидация номера
        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            throw new InvalidVehiclePlateException(plateNumber);
        }

        // 2. Ищем свободное место
        List<ParkingSpot> freeSpots = spotRepo.getAllFreeSpots();
        if (freeSpots.isEmpty()) {
            throw new NoFreeSpotsException();
        }
        ParkingSpot spot = freeSpots.get(0);

        // 3. Проверяем/создаем транспорт
        Vehicle vehicle = vehicleRepo.findByPlate(plateNumber);
        if (vehicle == null) {
            vehicle = new Vehicle(0, plateNumber);
            vehicleRepo.addVehicle(vehicle);
            vehicle = vehicleRepo.findByPlate(plateNumber); // Получаем с ID
        }

        // 4. Создаем бронь
        Reservation res = new Reservation(0, spot.getId(), vehicle.getId(), LocalDateTime.now());
        reservationRepo.create(res);

        // 5. Меняем статус места
        spotRepo.updateStatus(spot.getId(), true);
    }

    /**
     * User Story: Release a spot & Calculate cost
     */
    public void release(String plateNumber) throws Exception {
        Vehicle vehicle = vehicleRepo.findByPlate(plateNumber);
        if (vehicle == null) throw new Exception("Vehicle not found");

        Reservation activeRes = reservationRepo.findActiveByVehicle(vehicle.getId());
        if (activeRes == null) throw new Exception("No active reservation found");

        // Расчет стоимости
        double rate = tariffRepo.getStandardRate();
        LocalDateTime endTime = LocalDateTime.now();
        double cost = pricingService.calculateCost(activeRes.getStartTime(), endTime, rate);

        // Завершаем бронь в БД
        reservationRepo.finishReservation(activeRes.getId(), endTime, cost);

        // Освобождаем место
        spotRepo.updateStatus(activeRes.getSpotId(), false);

        System.out.println("Spot released! Total cost: " + cost);
    }
}