package entities;

public class Vehicle {
    private int id;
    private String plateNumber;

    public Vehicle(int id, String plateNumber){
        this.id = id;
        this.plateNumber = plateNumber;
    }
    public String getPlateNumber(){
        return plateNumber;
    }

}
