package repositories;

import entities.Reservation;

import java.time.LocalDateTime;

public interface IReservationRepository {
    void create(Reservation reservation); // Сохранить новую бронь
    Reservation findActiveByVehicle(int vehicleId); // Найти текущую бронь машины
    void finishReservation(int id, LocalDateTime endTime, double totalCost); // Закрыть
}
