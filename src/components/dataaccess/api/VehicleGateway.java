package components.dataaccess.api;

import entities.Vehicle;

public interface VehicleGateway {
    Vehicle findByPlate(String plate);
    void save(Vehicle vehicle);
}
