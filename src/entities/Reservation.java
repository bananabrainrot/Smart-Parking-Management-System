package entities;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private int spotId;
    private int vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Reservation(int id, int spotId, int vehicleId, LocalDateTime startTime) {
        this.id = id;
        this.spotId = spotId;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleId() {

        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getSpotId() {

        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public LocalDateTime getStartTime() {

        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
