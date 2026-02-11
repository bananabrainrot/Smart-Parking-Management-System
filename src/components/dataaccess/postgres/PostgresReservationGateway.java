package components.dataaccess.postgres;

import components.dataaccess.api.ReservationGateway;
import edu.aitu.oop3.db.DatabaseConnection;
import entities.Reservation;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class PostgresReservationGateway implements ReservationGateway {
    @Override
    public void create(Reservation reservation) {
        String sql = "INSERT INTO reservations (spot_id, vehicle_id, tariff_id, start_time, is_active) VALUES (?, ?, ?, ?, true)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, reservation.getSpotId());
            pstmt.setInt(2, reservation.getVehicleId());
            pstmt.setInt(3, reservation.getTariffId());
            pstmt.setTimestamp(4, Timestamp.valueOf(reservation.getStartTime()));
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating reservation", e);
        }
    }

    @Override
    public Optional<Reservation> findActiveByVehicleId(int vehicleId) {
        String sql = "SELECT * FROM reservations WHERE vehicle_id = ? AND is_active = true";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getInt("id"),
                            rs.getInt("spot_id"),
                            rs.getInt("vehicle_id"),
                            rs.getInt("tariff_id"),
                            rs.getTimestamp("start_time").toLocalDateTime()
                    );
                    return Optional.of(reservation);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active reservation", e);
        }
        return Optional.empty();
    }

    @Override
    public void finishReservation(int reservationId, BigDecimal totalCost) {
        String sql = "UPDATE reservations SET end_time = CURRENT_TIMESTAMP, total_cost = ?, is_active = false WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, totalCost);
            pstmt.setInt(2, reservationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error finishing reservation", e);
        }
    }

    @Override
    public int getActiveReservationsCount() {
        String sql = "SELECT COUNT(*) FROM reservations WHERE is_active = true";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error counting active reservations", e);
        }
    }
}
