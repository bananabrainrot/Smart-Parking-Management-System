package repositories;

import entities.ListResult;
import entities.ParkingSpot;

public interface IParkingSpotRepository {
    ListResult<ParkingSpot> getAllFreeSpots();
    void updateSpotStatus(int id, boolean isAvailable);
    ParkingSpot getById(int id);
}
