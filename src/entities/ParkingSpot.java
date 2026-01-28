package entities;

public class ParkingSpot {
    private int id;
    private String spotNumber;
    private boolean isOccupied;

    public ParkingSpot(int id, String spotNumber, boolean isOccupied){
        this.id = id;
        this.spotNumber = spotNumber;
        this.isOccupied = isOccupied;
    }
}
