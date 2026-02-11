package components.dataaccess.api;

import entities.Reservation;

import java.math.BigDecimal;
import java.util.Optional;

public interface ReservationGateway {
    void create(Reservation reservation);
    Optional<Reservation> findActiveByVehicleId(int vehicleId);
    void finishReservation(int reservationId, BigDecimal totalCost);
    int getActiveReservationsCount();
}
