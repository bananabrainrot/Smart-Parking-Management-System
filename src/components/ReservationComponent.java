package components;

import services.ReservationService;

public class ReservationComponent {
    private final ReservationService reservationService;

    public ReservationComponent(DataAccessComponent dataAccessComponent) {
        this.reservationService = new ReservationService(
                dataAccessComponent.reservations(),
                dataAccessComponent.parkingSpots(),
                dataAccessComponent.vehicles()
        );
    }

    public void reserveSpot(String plate, int spotId, int tariffId) {
        reservationService.processReservation(plate, spotId, tariffId);
    }
}
