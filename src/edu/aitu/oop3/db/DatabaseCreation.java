package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseCreation {

    public static void createTables() {

        String createSpotsTable = "CREATE TABLE IF NOT EXISTS parking_spots (" +
                "id SERIAL PRIMARY KEY, " +
                "spot_number VARCHAR(10) NOT NULL, " +
                "is_available BOOLEAN DEFAULT TRUE)";

        String createVehiclesTable = "CREATE TABLE IF NOT EXISTS vehicles (" +
                "id SERIAL PRIMARY KEY, " +
                "license_plate VARCHAR(20) UNIQUE NOT NULL, " +
                "owner_name VARCHAR(100), " +
                "type VARCHAR(50))";

        String createTariffsTable = "CREATE TABLE IF NOT EXISTS tariffs (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "rate_per_hour DECIMAL(10, 2), " +
                "vehicle_type VARCHAR(50))";

        String createReservationsTable = "CREATE TABLE IF NOT EXISTS reservations (" +
                "id SERIAL PRIMARY KEY, " +
                "spot_id INTEGER REFERENCES parking_spots(id), " +
                "vehicle_id INTEGER REFERENCES vehicles(id), " +
                "tariff_id INTEGER REFERENCES tariffs(id), " +
                "start_time TIMESTAMP, " +
                "end_time TIMESTAMP, " +
                "total_cost DECIMAL(10, 2), " +
                "is_active BOOLEAN DEFAULT TRUE)";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {


            stmt.execute(createSpotsTable);
            stmt.execute(createVehiclesTable);
            stmt.execute(createTariffsTable);
            stmt.execute(createReservationsTable);

            System.out.println("Tables checked/created successfully.");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
}