package entities;

public class SpotFactory {
    public ParkingSpot createSpot(SpotType type, int id, String spotNumber, Boolean isAvailable) {
        if (type == null) {
            return new StandardSpot(id, spotNumber, isAvailable);
        }
        switch (type) {
            case DISABLED:
                return new DisabledSpot(id, spotNumber, isAvailable);
            case ELECTRIC:
                return new ElectricSpot(id, spotNumber, isAvailable);
            case STANDARD:
            default:
                return new StandardSpot(id, spotNumber, isAvailable);
        }
    }
}
