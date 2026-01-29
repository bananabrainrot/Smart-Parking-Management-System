package repositories.implementations;

import edu.aitu.oop3.db.DatabaseConnection;
import entities.ParkingSpot;
import repositories.IParkingSpotRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresParkingSpotRepository implements IParkingSpotRepository {

    @Override
    public List<ParkingSpot> getAllFreeSpots() {
        List<ParkingSpot> spots = new ArrayList<>();
        String sql = "SELECT * FROM parking_spots WHERE is_available = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                spots.add(new ParkingSpot(rs.getInt("id"), rs.getString("spot_number"), rs.getBoolean("is_available")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return spots;
    }

    @Override
    public ParkingSpot getById(int id) {
        String sql = "SELECT * FROM parking_spots WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ParkingSpot(rs.getInt("id"), rs.getString("spot_number"), rs.getBoolean("is_available"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void updateSpotStatus(int id, boolean isAvailable) {
        String sql = "UPDATE parking_spots SET is_available = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isAvailable);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}