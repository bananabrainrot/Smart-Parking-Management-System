import components.DataAccessComponent;
import components.MonitoringComponent;
import components.PaymentComponent;
import components.ReportingComponent;
import components.ReservationComponent;
import config.ParkingPolicy;
import config.TariffPlan;
import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.db.DatabaseCreation;
import entities.SpotType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParkingPolicy parkingPolicy = new ParkingPolicy.Builder()
                .addZone("A", SpotType.STANDARD)
                .addZone("D", SpotType.DISABLED)
                .addZone("E", SpotType.ELECTRIC)
                .addTariff(1, "Standard", BigDecimal.valueOf(150.00))
                .addTariff(2, "Night", BigDecimal.valueOf(110.00))
                .build();

        DatabaseCreation.createTables();
        setupInitialData(parkingPolicy);

        DataAccessComponent dataAccessComponent = new DataAccessComponent(parkingPolicy);
        ReservationComponent reservationComponent = new ReservationComponent(dataAccessComponent);
        PaymentComponent paymentComponent = new PaymentComponent(dataAccessComponent);
        MonitoringComponent monitoringComponent = new MonitoringComponent(dataAccessComponent);
        ReportingComponent reportingComponent = new ReportingComponent(monitoringComponent, parkingPolicy);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SMART PARKING SYSTEM ---");
            System.out.println("1. Park Vehicle (ReservationComponent)");
            System.out.println("2. Release Spot (PaymentComponent)");
            System.out.println("3. Show All Free Spots (MonitoringComponent)");
            System.out.println("4. Show Report (ReportingComponent)");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            try {
                String choiceStr = sc.nextLine();
                int choice = Integer.parseInt(choiceStr);

                switch (choice) {
                    case 1:
                        System.out.print("Enter License Plate: ");
                        String plate = sc.nextLine();
                        System.out.print("Enter Spot ID: ");
                        int spotId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Tariff ID: ");
                        int tariffId = Integer.parseInt(sc.nextLine());

                        reservationComponent.reserveSpot(plate, spotId, tariffId);
                        System.out.println("Vehicle parked successfully!");
                        break;

                    case 2:
                        System.out.print("Enter License Plate to exit: ");
                        String exitPlate = sc.nextLine();
                        paymentComponent.checkoutAndPay(exitPlate);
                        break;

                    case 3:
                        System.out.println("\n--- Free Spots ---");
                        monitoringComponent.showFreeSpots().getItems().forEach(s ->
                                System.out.println("ID: " + s.getId() + " | Number: " + s.getSpotNumber()));
                        break;

                    case 4:
                        reportingComponent.printOperationalReport();
                        printExtensibilityHint();
                        break;

                    case 0:
                        System.out.println("Shutting down...");
                        return;

                    default:
                        System.out.println("Invalid option! Try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Please enter a valid number.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void setupInitialData(ParkingPolicy parkingPolicy) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO parking_spots (spot_number, is_available) VALUES ('A1', true) ON CONFLICT DO NOTHING");
            stmt.execute("INSERT INTO parking_spots (spot_number, is_available) VALUES ('A2', true) ON CONFLICT DO NOTHING");
            stmt.execute("INSERT INTO parking_spots (spot_number, is_available) VALUES ('E1', true) ON CONFLICT DO NOTHING");

            for (TariffPlan tariffPlan : parkingPolicy.getTariffPlans()) {
                upsertTariff(conn, tariffPlan);
            }

            System.out.println("Database check: Ready for operations.");
        } catch (SQLException e) {
            System.err.println("Setup error: " + e.getMessage());
        }
    }

    private static void upsertTariff(Connection conn, TariffPlan tariffPlan) throws SQLException {
        String sql = "INSERT INTO tariffs (id, name, rate_per_hour) VALUES (?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, rate_per_hour = EXCLUDED.rate_per_hour";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, tariffPlan.getId());
            preparedStatement.setString(2, tariffPlan.getName());
            preparedStatement.setBigDecimal(3, tariffPlan.getRatePerHour());
            preparedStatement.executeUpdate();
        }
    }

    private static void printExtensibilityHint() {
        System.out.println("\nExtensibility note:");
        System.out.println("- To add a zone, append .addZone(\"VIP\", SpotType.STANDARD) in ParkingPolicy.Builder.");
        System.out.println("- To add a tariff, append .addTariff(3, \"Weekend\", BigDecimal.valueOf(90)).");
        System.out.println("No component code changes are required (OCP), and parking policy changes remain grouped in one place (CCP).");
    }
}
