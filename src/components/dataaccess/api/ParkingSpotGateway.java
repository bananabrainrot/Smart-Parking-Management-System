package components.dataaccess.api;

import entities.ListResult;
import entities.ParkingSpot;

public interface ParkingSpotGateway {
    ListResult<ParkingSpot> getAllFreeSpots();
    ParkingSpot findById(int id);
    void updateAvailability(int id, boolean isAvailable);
}
