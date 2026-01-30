package repositories;

import entities.ParkingSpot;
import java.util.List;

public interface IParkingSpotRepository {
    List<ParkingSpot> getAllFreeSpots();
    void updateSpotStatus(int id, boolean isAvailable);
    ParkingSpot getById(int id);
}