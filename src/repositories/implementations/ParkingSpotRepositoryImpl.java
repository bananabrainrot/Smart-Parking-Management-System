package repositories.implementations;

import data.IDB;
import entities.ParkingSpot;
import repositories.IParkingSpotRepository;
import exception.NoFreeSpotsException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpotRepositoryImpl implements IParkingSpotRepository {
    private final IDB db; // Используем интерфейс БД

    public ParkingSpotRepositoryImpl(IDB db) {
        this.db = db;
    }

    @Override
    public ParkingSpot getFreeSpot() throws NoFreeSpotsException {
        return null;
    }

    @Override
    public List<ParkingSpot> getAllFreeSpots() {
        List<ParkingSpot> spots = new ArrayList<>();
        String sql = "SELECT * FROM parking_spots WHERE is_occupied = false";

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                spots.add(new ParkingSpot(
                        rs.getInt("id"),
                        rs.getString("spot_number"),
                        rs.getBoolean("is_occupied")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ошибка БД: " + e.getMessage());
        }
        return spots;
    }

    @Override
    public void updateStatus(int id, boolean isOccupied) {
        String sql = "UPDATE parking_spots SET is_occupied = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, isOccupied);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}