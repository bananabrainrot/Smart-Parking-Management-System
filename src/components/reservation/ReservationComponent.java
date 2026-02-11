package components.reservation;

public class ReservationComponent {
    private final ReservationService reservationService;

    public ReservationComponent(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public ReservationService reservationService() {
        return reservationService;
    }
}
