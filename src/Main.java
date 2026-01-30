import edu.aitu.oop3.db.DatabaseCreation;
import repositories.*;
import repositories.implementations.*;
import services.ReservationService;
import entities.ParkingSpot;
import edu.aitu.oop3.db.DatabaseConnection;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        DatabaseCreation.createTables();

        IParkingSpotRepository spotRepo = new PostgresParkingSpotRepository();
        IVehicleRepository vehicleRepo = new PostgresVehicleRepository();
        IReservationRepository resRepo = new PostgresReservationRepository();

        ReservationService parkingManager = new ReservationService(resRepo, spotRepo, vehicleRepo);

        try {
            setupInitialData();

            System.out.println("Starting test reservation...");
            parkingManager.processReservation("777AAA01", 1, 1);
            System.out.println("Success! Check your Supabase tables.");

        } catch (Exception e) {
            System.err.println("Operation failed: " + e.getMessage());
        }
    }

    private static void setupInitialData() {
        String sql = "INSERT INTO parking_spots (spot_number, is_available) VALUES ('A1', true) ON CONFLICT DO NOTHING";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ignored) {}
    }
}