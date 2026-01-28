package repositories;

import entities.ParkingSpot;
import exception.NoFreeSpotsException;

import java.util.List;

public interface IParkingSpotRepository {
    ParkingSpot getFreeSpot() throws NoFreeSpotsException; // Найти одно свободное
    List<ParkingSpot> getAllFreeSpots(); // Список всех свободных
    void updateStatus(int id, boolean isOccupied); // Занять или освободить
}
