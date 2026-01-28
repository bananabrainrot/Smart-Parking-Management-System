package edu.aitu.oop3.services;

import entities.*;
import exception.*;
import repositories.*;
import java.time.LocalDateTime;
import services.*;

public class ReservationService {
    private final IParkingSpotRepository spotRepo;
    private final IReservationRepository resRepo;
    private final PricingService pricingService;

    public ReservationService(IParkingSpotRepository spotRepo,
                              IReservationRepository resRepo,
                              PricingService pricingService) {
        this.spotRepo = spotRepo;
        this.resRepo = resRepo;
        this.pricingService = pricingService;
    }

    public void reserveSpot(String plate, int vehicleId) {
        var freeSpots = spotRepo.getAllFreeSpots();
        if (freeSpots.isEmpty()) throw new NoFreeSpotsException();

        ParkingSpot spot = freeSpots.get(0);
        Reservation res = new Reservation(0, spot.getId(), vehicleId, LocalDateTime.now());

        resRepo.create(res);
        spotRepo.updateStatus(spot.getId(), true);
    }

    public void releaseSpot(int vehicleId, double rate) {
        Reservation res = resRepo.findActiveByVehicle(vehicleId);
        if (res == null) throw new ReservationStatusException("No active reservation");

        LocalDateTime now = LocalDateTime.now();
        double cost = pricingService.calculateCost(res.getStartTime(), now, rate);

        resRepo.finish(res.getId(), now, cost);
        spotRepo.updateStatus(res.getSpotId(), false);
    }
}