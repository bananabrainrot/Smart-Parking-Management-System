package entities;

import java.math.BigDecimal;

public class Tariff {
    private int id;
    private String name;
    private BigDecimal ratePerHour;
    private String vehicleType;

    public Tariff(int id, String name, BigDecimal ratePerHour) {
        this.id = id;
        this.name = name;
        this.ratePerHour = ratePerHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(BigDecimal ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
