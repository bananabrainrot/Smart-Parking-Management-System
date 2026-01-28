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



    public int getVehicleId() {
        return vehicleId;
    }

    public int getSpotId() {
        return spotId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public static void
}
