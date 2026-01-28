package entities;

import java.sql.Time;

public class Tariff {
    private int id;
    private String name;
    private double ratePerHour;

    public Tariff(int id, String name, double ratePerHour) {
        this.id = id;
        this.name = name;
        this.ratePerHour = ratePerHour;
    }
}
