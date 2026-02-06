package entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {
    private final String plate;
    private final int spotId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final BigDecimal totalCost;

    private Invoice(Builder builder) {
        this.plate = builder.plate;
        this.spotId = builder.spotId;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.totalCost = builder.totalCost;
    }

    public String getPlate() {
        return plate;
    }

    public int getSpotId() {
        return spotId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public static class Builder {
        private String plate;
        private int spotId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private BigDecimal totalCost;

        public Builder plate(String plate) {
            this.plate = plate;
            return this;
        }

        public Builder spotId(int spotId) {
            this.spotId = spotId;
            return this;
        }

        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder totalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
