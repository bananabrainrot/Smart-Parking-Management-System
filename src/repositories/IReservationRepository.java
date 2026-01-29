package repositories;

import entities.Reservation;
import java.util.List;

public interface IReservationRepository {
    void create(Reservation reservation);
    void finishReservation(int id, java.math.BigDecimal cost);
    Reservation findActiveByVehicleId(int vehicleId);
}