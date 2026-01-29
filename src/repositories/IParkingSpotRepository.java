package repositories;

import entities.ParkingSpot;
import java.util.List;

public interface IParkingSpotRepository {
    List<ParkingSpot> getAllFreeSpots(); // User story: list free spots
    void updateSpotStatus(int id, boolean isAvailable); // Для заезда/выезда
    ParkingSpot getById(int id);
}