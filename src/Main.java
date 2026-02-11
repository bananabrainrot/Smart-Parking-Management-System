import components.dataaccess.DataAccessComponent;
import components.monitoring.MonitoringComponent;
import components.monitoring.ParkingStatus;
import components.monitoring.MonitoringService;
import components.payment.*;
import components.reporting.PopularHoursReport;
import components.reporting.ReportingComponent;
import components.reporting.ReportingService;
import components.reporting.RevenueReport;
import components.reservation.ReservationComponent;
import components.reservation.ReservationService;
import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.db.DatabaseCreation;
import entities.Invoice;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseCreation.createTables();
        setupInitialData();

        DataAccessComponent dataAccess = new DataAccessComponent();

        PricingService pricingService = new PricingService(List.of(
                new EarlyBirdTariffPricingStrategy(),
                new HourlyTariffPricingStrategy()
        ));
        PaymentComponent paymentComponent = new PaymentComponent(
                new PaymentService(pricingService, dataAccess.tariffs(), dataAccess.reservations())
        );
        ReservationComponent reservationComponent = new ReservationComponent(
                new ReservationService(dataAccess.reservations(), dataAccess.parkingSpots(), dataAccess.vehicles(), paymentComponent.paymentService())
        );
        MonitoringComponent monitoringComponent = new MonitoringComponent(
                new MonitoringService(dataAccess.parkingSpots(), dataAccess.reservations())
        );
        ReportingComponent reportingComponent = new ReportingComponent(
                new ReportingService(dataAccess.reporting())
        );

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SMART PARKING SYSTEM ---");
            System.out.println("1. Park Vehicle (Entry)");
            System.out.println("2. Release Spot (Exit & Pay)");
            System.out.println("3. Monitoring: Current Parking Status");
            System.out.println("4. Reporting: Revenue (last 24h)");
            System.out.println("5. Reporting: Popular Hours (last 24h)");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter License Plate: ");
                        String plate = sc.nextLine();
                        System.out.print("Enter Spot ID: ");
                        int spotId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Tariff ID (1=Standard, 2=EarlyBird): ");
                        int tariffId = Integer.parseInt(sc.nextLine());

                        reservationComponent.reservationService().processReservation(plate, spotId, tariffId);
                        System.out.println("Vehicle parked successfully!");
                    }
                    case 2 -> {
                        System.out.print("Enter License Plate to exit: ");
                        String exitPlate = sc.nextLine();
                        Invoice invoice = reservationComponent.reservationService().releaseSpot(exitPlate);
                        System.out.println("Spot released! Total cost: " + invoice.getTotalCost());
                    }
                    case 3 -> {
                        ParkingStatus status = monitoringComponent.monitoringService().getCurrentStatus();
                        System.out.println("Free spots: " + status.getFreeSpots());
                        System.out.println("Active reservations: " + status.getActiveReservations());
                    }
                    case 4 -> {
                        LocalDateTime to = LocalDateTime.now();
                        LocalDateTime from = to.minusHours(24);
                        RevenueReport report = reportingComponent.reportingService().generateRevenueReport(from, to);
                        System.out.println("Revenue from " + report.getFrom() + " to " + report.getTo() + ": " + report.getTotalRevenue());
                    }
                    case 5 -> {
                        LocalDateTime to = LocalDateTime.now();
                        LocalDateTime from = to.minusHours(24);
                        PopularHoursReport report = reportingComponent.reportingService().generatePopularHoursReport(from, to);
                        System.out.println("Popular hours (hour -> reservations):");
                        report.getReservationsByHour().forEach((hour, count) ->
                                System.out.println(hour + ":00 -> " + count));
                    }
                    case 0 -> {
                        System.out.println("Shutting down...");
                        return;
                    }
                    default -> System.out.println("Invalid option! Try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Please enter a valid number.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void setupInitialData() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO parking_spots (spot_number, is_available) VALUES ('A1', true) ON CONFLICT DO NOTHING");
            stmt.execute("INSERT INTO parking_spots (spot_number, is_available) VALUES ('A2', true) ON CONFLICT DO NOTHING");
            stmt.execute("INSERT INTO parking_spots (spot_number, is_available) VALUES ('E1', true) ON CONFLICT DO NOTHING");

            stmt.execute("INSERT INTO tariffs (id, name, rate_per_hour) VALUES (1, 'Standard', 150.00) ON CONFLICT (id) DO NOTHING");
            stmt.execute("INSERT INTO tariffs (id, name, rate_per_hour) VALUES (2, 'EarlyBird', 900.00) ON CONFLICT (id) DO NOTHING");

            System.out.println("Database check: Ready for operations.");
        } catch (SQLException e) {
            System.err.println("Setup error: " + e.getMessage());
        }
    }
}
