package repository.impl;

import edu.aitu.oop3.db.DatabaseConnection;
import entities.ParkingSpot;
import repositories.IParkingSpotRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpotRepositoryImpl implements IParkingSpotRepository {
    private final DatabaseConnection db;

    public ParkingSpotRepositoryImpl(Dat db) {
        this.db = db;
    }

    @Override
    public List<ParkingSpot> getAllFreeSpots() {
        List<ParkingSpot> spots = new ArrayList<>();
        String sql = "SELECT * FROM parking_spots WHERE isOccupied = false";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                spots.add(new ParkingSpot(rs.getInt("id"), rs.getString("spotNumber"), rs.getBoolean("isOccupied")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return spots;
    }

    @Override
    public void updateStatus(int id, boolean isOccupied) {
        String sql = "UPDATE parking_spots SET isOccupied = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isOccupied);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}