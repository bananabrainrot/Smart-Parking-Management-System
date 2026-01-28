package repositories.implementations;

import data.IDB;
import entities.Reservation;
import repositories.IReservationRepository;
import java.sql.*;
import java.time.LocalDateTime;

public class ReservationRepositoryImpl implements IReservationRepository {
    private final IDB db;

    public ReservationRepositoryImpl(IDB db) {
        this.db = db;
    }

    @Override
    public void create(Reservation res) {
        String sql = "INSERT INTO reservations (spot_id, vehicle_id, start_time) VALUES (?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, res.getSpotId());
            pstmt.setInt(2, res.getVehicleId());
            pstmt.setTimestamp(3, Timestamp.valueOf(res.getStartTime()));
            pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation findActiveByVehicle(int vehicleId) {
        // Ищем бронь, где end_time еще не заполнено (NULL)
        String sql = "SELECT * FROM reservations WHERE vehicle_id = ? AND end_time IS NULL";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getInt("id"),
                        rs.getInt("spot_id"),
                        rs.getInt("vehicle_id"),
                        rs.getTimestamp("start_time").toLocalDateTime()
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void finishReservation(int id, LocalDateTime endTime, double totalCost) {
        String sql = "UPDATE reservations SET end_time = ?, total_cost = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(endTime));
            pstmt.setDouble(2, totalCost);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}