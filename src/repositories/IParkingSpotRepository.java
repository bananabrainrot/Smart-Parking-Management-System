package repositories;

import entities.*;
import java.util.List;
import java.time.LocalDateTime;

public interface IParkingSpotRepository {
    List<ParkingSpot> getAllFreeSpots();
    void updateStatus(int id, boolean isOccupied);
}

public interface IVehicleRepository {
    Vehicle findByPlate(String plate);
    void add(Vehicle vehicle);
}

public interface IReservationRepository {
    void create(Reservation res);
    Reservation findActiveByVehicle(int vehicleId);
    void finish(int id, LocalDateTime endTime, double cost);
}

public interface ITariffRepository {
    Tariff getByName(String name);
}