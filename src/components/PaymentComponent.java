package components;

import services.ReservationService;

public class PaymentComponent {
    private final ReservationService reservationService;

    public PaymentComponent(DataAccessComponent dataAccessComponent) {
        this.reservationService = new ReservationService(
                dataAccessComponent.reservations(),
                dataAccessComponent.parkingSpots(),
                dataAccessComponent.vehicles()
        );
    }

    public void checkoutAndPay(String plate) {
        reservationService.releaseSpot(plate);
    }
}
