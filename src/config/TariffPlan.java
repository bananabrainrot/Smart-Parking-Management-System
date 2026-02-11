package config;

import java.math.BigDecimal;

public class TariffPlan {
    private final int id;
    private final String name;
    private final BigDecimal ratePerHour;

    public TariffPlan(int id, String name, BigDecimal ratePerHour) {
        this.id = id;
        this.name = name;
        this.ratePerHour = ratePerHour;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRatePerHour() {
        return ratePerHour;
    }
}
