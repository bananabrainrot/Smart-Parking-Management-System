package repositories;

import entities.Vehicle;

public interface IVehicleRepository {
    void save(Vehicle vehicle);
    Vehicle findByPlate(String plate);
}