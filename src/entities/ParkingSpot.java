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

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(String spotNumber){
        this.spotNumber = spotNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
         isOccupied = occupied;
    }
}
