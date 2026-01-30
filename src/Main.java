import edu.aitu.oop3.db.DatabaseCreation;
import edu.aitu.oop3.db.DatabaseConnection;
import repositories.*;
import repositories.implementations.*;
import services.ReservationService;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DatabaseCreation.createTables();


        setupInitialData();

        IParkingSpotRepository spotRepo = new PostgresParkingSpotRepository();
        IVehicleRepository vehicleRepo = new PostgresVehicleRepository();
        IReservationRepository resRepo = new PostgresReservationRepository();

        ReservationService parkingManager = new ReservationService(resRepo, spotRepo, vehicleRepo);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SMART PARKING SYSTEM ---");
            System.out.println("1. Park Vehicle (Entry)");
            System.out.println("2. Release Spot (Exit & Pay)");
            System.out.println("3. Show All Free Spots");
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
                        System.out.print("Enter Tariff ID (1 for Standard): ");
                        int tariffId = Integer.parseInt(sc.nextLine());

                        parkingManager.processReservation(plate, spotId, tariffId);
                        System.out.println("Vehicle parked successfully!");
                        break;

                    case 2:
                        System.out.print("Enter License Plate to exit: ");
                        String exitPlate = sc.nextLine();
                        parkingManager.releaseSpot(exitPlate);
                        break;

                    case 3:
                        System.out.println("\n--- Free Spots ---");
                        spotRepo.getAllFreeSpots().forEach(s ->
                                System.out.println("ID: " + s.getId() + " | Number: " + s.getSpotNumber()));
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

    private static void setupInitialData() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO parking_spots (spot_number, is_available) VALUES ('A1', true) ON CONFLICT DO NOTHING");
            stmt.execute("INSERT INTO parking_spots (spot_number, is_available) VALUES ('A2', true) ON CONFLICT DO NOTHING");

            stmt.execute("INSERT INTO tariffs (id, name, rate_per_hour) " +
                    "VALUES (1, 'Standard', 150.00) ON CONFLICT (id) DO NOTHING");

            System.out.println("Database check: Ready for operations.");
        } catch (SQLException e) {
            System.err.println("Setup error: " + e.getMessage());
        }
    }
}