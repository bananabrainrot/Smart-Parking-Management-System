package edu.aitu.oop3.db;

import java.sql.*;

public class DatabaseCreation {
    // Название метода теперь совпадает с вызовом в Main
    public static void createTables() {
        parking_spotsDB();
        vehiclesDB();
        tariffsDB(); // Сначала создаем их
        reservationDB(); // Бронирование — в последнюю очередь
    }

    private static void vehiclesDB() {
        execute("CREATE TABLE IF NOT EXISTS vehicles (id SERIAL PRIMARY KEY, license_plate VARCHAR(15) UNIQUE)");
    }

    private static void parking_spotsDB() {
        execute("CREATE TABLE IF NOT EXISTS parking_spots (id SERIAL PRIMARY KEY, spot_number VARCHAR(10) UNIQUE, is_available BOOLEAN DEFAULT TRUE)");
    }

    private static void tariffsDB() {
        execute("CREATE TABLE IF NOT EXISTS tariffs (id SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL, rate_per_hour NUMERIC(10, 2) NOT NULL)");
    }

    private static void reservationDB() {
        execute("""
            CREATE TABLE IF NOT EXISTS reservations (
                id SERIAL PRIMARY KEY,
                spot_id INT REFERENCES parking_spots(id),
                vehicle_id INT REFERENCES vehicles(id),
                start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                end_time TIMESTAMP,
                total_cost NUMERIC(10, 2)
            )
            """);
    }

    private static void execute(String sql) {
        try (Connection conn = DatabaseConnection.getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}