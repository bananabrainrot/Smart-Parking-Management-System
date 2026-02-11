package components.dataaccess.postgres;

import components.dataaccess.api.ParkingSpotGateway;
import edu.aitu.oop3.db.DatabaseConnection;
import entities.ListResult;
import entities.ParkingSpot;
import entities.SpotFactory;
import entities.SpotType;

import java.sql.*;
import java.util.ArrayList;

public class PostgresParkingSpotGateway implements ParkingSpotGateway {
    private final SpotFactory spotFactory = new SpotFactory();

    @Override
    public ListResult<ParkingSpot> getAllFreeSpots() {
        ArrayList<ParkingSpot> spots = new ArrayList<>();
        String sql = "SELECT * FROM parking_spots WHERE is_available = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String spotNumber = rs.getString("spot_number");
                SpotType spotType = SpotType.fromSpotNumber(spotNumber);
                spots.add(spotFactory.createSpot(spotType, rs.getInt("id"), spotNumber, rs.getBoolean("is_available")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading free spots", e);
        }
        return new ListResult<>(spots, spots.size());
    }

    @Override
    public ParkingSpot findById(int id) {
        String sql = "SELECT * FROM parking_spots WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String spotNumber = rs.getString("spot_number");
                    SpotType spotType = SpotType.fromSpotNumber(spotNumber);
                    return spotFactory.createSpot(spotType, rs.getInt("id"), spotNumber, rs.getBoolean("is_available"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding spot by id", e);
        }
        return null;
    }

    @Override
    public void updateAvailability(int id, boolean isAvailable) {
        String sql = "UPDATE parking_spots SET is_available = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isAvailable);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating spot status", e);
        }
    }
}
