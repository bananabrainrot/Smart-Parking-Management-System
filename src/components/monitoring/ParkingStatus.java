package components.monitoring;

public class ParkingStatus {
    private final int freeSpots;
    private final int activeReservations;

    public ParkingStatus(int freeSpots, int activeReservations) {
        this.freeSpots = freeSpots;
        this.activeReservations = activeReservations;
    }

    public int getFreeSpots() {
        return freeSpots;
    }

    public int getActiveReservations() {
        return activeReservations;
    }
}
