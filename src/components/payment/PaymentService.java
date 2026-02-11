package components.payment;

import components.dataaccess.api.ReservationGateway;
import components.dataaccess.api.TariffGateway;
import entities.Invoice;
import entities.Reservation;
import entities.Tariff;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    private final PricingService pricingService;
    private final TariffGateway tariffGateway;
    private final ReservationGateway reservationGateway;

    public PaymentService(PricingService pricingService,
                          TariffGateway tariffGateway,
                          ReservationGateway reservationGateway) {
        this.pricingService = pricingService;
        this.tariffGateway = tariffGateway;
        this.reservationGateway = reservationGateway;
    }

    public Invoice closeReservationAndCreateInvoice(String plate, Reservation reservation) {
        Tariff tariff = tariffGateway.findById(reservation.getTariffId())
                .orElseThrow(() -> new IllegalStateException("Tariff not found: " + reservation.getTariffId()));

        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal totalCost = pricingService.calculateCost(reservation.getStartTime(), endTime, tariff);

        reservationGateway.finishReservation(reservation.getId(), totalCost);

        return new Invoice.Builder()
                .plate(plate)
                .spotId(reservation.getSpotId())
                .startTime(reservation.getStartTime())
                .endTime(endTime)
                .totalCost(totalCost)
                .build();
    }
}
