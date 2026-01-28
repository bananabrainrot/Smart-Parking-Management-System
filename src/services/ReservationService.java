package services;

import entities.ParkingSpot;
import exception.NoFreeSpotsException;
import repositories.IParkingSpotRepository;
import repositories.IReservationRepository;

import java.util.List;

public class ReservationService {
    private final IReservationRepository reservationRepo;
    private final IParkingSpotRepository spotRepo;
    private final PricingService pricingService;

    public ReservationService(IReservationRepository resRepo,
                              IParkingSpotRepository spotRepo,
                              PricingService pricingService) {
        this.reservationRepo = resRepo;
        this.spotRepo = spotRepo;
        this.pricingService = pricingService;
    }

    public void reserveSpot(String plateNumber) throws NoFreeSpotsException {
        // 1. Ищем свободное место через репозиторий
        List<ParkingSpot> freeSpots = spotRepo.getAllFreeSpots();

        if (freeSpots.isEmpty()) {
            throw new NoFreeSpotsException(); // Твоё кастомное исключение!
        }

        ParkingSpot spot = freeSpots.get(0); // Берем первое попавшееся

        // 2. Меняем статус места в БД
        spotRepo.updateSpotStatus(spot.getId(), true);

        // 3. Создаем запись о бронировании
        // (Здесь еще логика поиска/создания Vehicle)
        // reservationRepo.createReservation(...);
    }
}


//package services;
//
//import repository.*;
//import models.*;
//import exceptions.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class ReservationService {
//    private final IParkingSpotRepository spotRepo;
//    private final IVehicleRepository vehicleRepo;
//    private final IReservationRepository reservationRepo;
//    private final ITariffRepository tariffRepo;
//    private final PricingService pricingService;
//
//    // SOLID: Dependency Injection через конструктор
//    public ReservationService(IParkingSpotRepository spotRepo,
//                              IVehicleRepository vehicleRepo,
//                              IReservationRepository reservationRepo,
//                              ITariffRepository tariffRepo,
//                              PricingService pricingService) {
//        this.spotRepo = spotRepo;
//        this.vehicleRepo = vehicleRepo;
//        this.reservationRepo = reservationRepo;
//        this.tariffRepo = tariffRepo;
//        this.pricingService = pricingService;
//    }
//
//    // Резервирование места (User Story 1)
//    public void reserveSpot(String plateNumber) throws NoFreeSpotsException, ReservationActiveException {
//        // 1. Проверяем, нет ли уже активной брони у этой машины
//        Vehicle vehicle = vehicleRepo.findByPlate(plateNumber);
//        if (vehicle != null) {
//            if (reservationRepo.findActiveByVehicle(vehicle.getId()) != null) {
//                throw new ReservationActiveException("Эта машина уже припаркована!");
//            }
//        } else {
//            // Если машины нет в базе — создаем её
//            vehicle = new Vehicle(0, plateNumber);
//            vehicleRepo.addVehicle(vehicle);
//            vehicle = vehicleRepo.findByPlate(plateNumber); // Получаем ID из БД
//        }
//
//        // 2. Ищем свободное место
//        List<ParkingSpot> freeSpots = spotRepo.getAllFreeSpots();
//        if (freeSpots.isEmpty()) {
//            throw new NoFreeSpotsException();
//        }
//        ParkingSpot spot = freeSpots.get(0);
//
//        // 3. Создаем бронь и помечаем место как занятое
//        Reservation res = new Reservation(0, spot.getId(), vehicle.getId(), LocalDateTime.now());
//        reservationRepo.create(res);
//        spotRepo.updateStatus(spot.getId(), true);
//
//        System.out.println("Место " + spot.getSpotNumber() + " успешно забронировано!");
//    }
//
//    // Освобождение места и расчет стоимости (User Story 2 & 4)
//    public void releaseSpot(String plateNumber) throws ReservationStatusException {
//        Vehicle vehicle = vehicleRepo.findByPlate(plateNumber);
//        if (vehicle == null) throw new ReservationStatusException("Машина не найдена");
//
//        Reservation activeRes = reservationRepo.findActiveByVehicle(vehicle.getId());
//        if (activeRes == null) throw new ReservationStatusException("Активная бронь отсутствует");
//
//        // Расчет цены
//        double rate = tariffRepo.getRateByName("Standard");
//        LocalDateTime now = LocalDateTime.now();
//        double totalCost = pricingService.calculate(activeRes.getStartTime(), now, rate);
//
//        // Завершаем в БД
//        reservationRepo.finishReservation(activeRes.getId(), now, totalCost);
//        spotRepo.updateStatus(activeRes.getSpotId(), false);
//
//        System.out.println("Парковка завершена. К оплате: " + totalCost + " тенге.");
//    }
//}