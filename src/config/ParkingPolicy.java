package config;

import entities.SpotType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParkingPolicy {
    private final Map<String, SpotType> zoneRules;
    private final List<TariffPlan> tariffPlans;

    private ParkingPolicy(Map<String, SpotType> zoneRules, List<TariffPlan> tariffPlans) {
        this.zoneRules = zoneRules;
        this.tariffPlans = tariffPlans;
    }

    public SpotType resolveSpotType(String spotNumber) {
        if (spotNumber == null || spotNumber.isBlank()) {
            return SpotType.STANDARD;
        }

        String normalized = spotNumber.trim().toUpperCase();
        for (Map.Entry<String, SpotType> entry : zoneRules.entrySet()) {
            if (normalized.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return SpotType.STANDARD;
    }

    public List<TariffPlan> getTariffPlans() {
        return List.copyOf(tariffPlans);
    }

    public static class Builder {
        private final Map<String, SpotType> zoneRules = new LinkedHashMap<>();
        private final List<TariffPlan> tariffPlans = new ArrayList<>();

        public Builder addZone(String prefix, SpotType spotType) {
            zoneRules.put(prefix.toUpperCase(), spotType);
            return this;
        }

        public Builder addTariff(int id, String name, BigDecimal ratePerHour) {
            tariffPlans.add(new TariffPlan(id, name, ratePerHour));
            return this;
        }

        public ParkingPolicy build() {
            if (zoneRules.isEmpty()) {
                addZone("A", SpotType.STANDARD);
                addZone("D", SpotType.DISABLED);
                addZone("E", SpotType.ELECTRIC);
            }

            if (tariffPlans.isEmpty()) {
                addTariff(1, "Standard", BigDecimal.valueOf(150));
            }

            return new ParkingPolicy(new LinkedHashMap<>(zoneRules), new ArrayList<>(tariffPlans));
        }
    }
}
