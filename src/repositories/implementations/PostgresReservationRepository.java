package repositories.implementations;

import edu.aitu.oop3.db.DatabaseConnection;
import entities.Reservation;
import repositories.IReservationRepository;

import java.sql.*;
import java.math.BigDecimal;

public class PostgresReservationRepository implements IReservationRepository {

    @Override
    public void create(Reservation res) {
        String sql = "INSERT INTO reservations (spot_id, vehicle_id, tariff_id, start_time, is_active) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, res.getSpotId());
            pstmt.setInt(2, res.getVehicleId());
            pstmt.setInt(3, res.getTariffId());
            pstmt.setTimestamp(4, Timestamp.valueOf(res.getStartTime()));
            pstmt.setBoolean(5, true);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finishReservation(int id, BigDecimal totalCost) {
        String sql = "UPDATE reservations SET end_time = CURRENT_TIMESTAMP, total_cost = ?, is_active = false WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, totalCost);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation findActiveByVehicleId(int vehicleId) {
        String sql = "SELECT * FROM reservations WHERE vehicle_id = ? AND is_active = true";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                            rs.getInt("id"),
                            rs.getInt("spot_id"),
                            rs.getInt("vehicle_id"),
                            rs.getInt("tariff_id"),
                            rs.getTimestamp("start_time").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}