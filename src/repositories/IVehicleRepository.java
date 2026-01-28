package repositories;

import entities.Vehicle;

public interface IVehicleRepository {
    Vehicle findByPlate(String plate); // Найти машину по номеру
    void addVehicle(Vehicle vehicle); // Сохранить новую машину
}
