package entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private int spotId;      // Ссылка на ParkingSpot
    private int vehicleId;   // Ссылка на Vehicle
    private int tariffId;    // Ссылка на Tariff (чтобы знать, по какой ставке считать)

    private LocalDateTime startTime;
    private LocalDateTime endTime; // Заполняется, когда машина уезжает
    private BigDecimal totalCost;  // Итоговая сумма (User story: calculate cost)
    private boolean isActive;      // Для Exception: reservation already active

    public Reservation() {
    }

    public Reservation(int id, int spotId, int vehicleId, int tariffId, LocalDateTime startTime) {
        this.id = id;
        this.spotId = spotId;
        this.vehicleId = vehicleId;
        this.tariffId = tariffId;
        this.startTime = startTime;
        this.isActive = true;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSpotId() { return spotId; }
    public void setSpotId(int spotId) { this.spotId = spotId; }

    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public int getTariffId() { return tariffId; }
    public void setTariffId(int tariffId) { this.tariffId = tariffId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}