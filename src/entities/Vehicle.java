package entities;

public class Vehicle {
    private int id;
    private String licensePlate;
    private String ownerName;
    private String type;
    public Vehicle(){
    }

    public Vehicle(int id, String licensePlate, String ownerName, String type){
        this. id = id;
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
