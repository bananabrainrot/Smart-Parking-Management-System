package repositories.implementations;

import edu.aitu.oop3.db.DatabaseConnection;
import entities.Vehicle;
import repositories.IVehicleRepository;

import java.sql.*;

public class PostgresVehicleRepository implements IVehicleRepository {

    @Override
    public void save(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (license_plate, owner_name, type) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, vehicle.getLicensePlate());
            pstmt.setString(2, vehicle.getOwnerName());
            pstmt.setString(3, vehicle.getType());

            pstmt.executeUpdate();

            // Получаем сгенерированный базой ID и записываем его в объект
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vehicle.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving vehicle: " + e.getMessage());
        }
    }

    @Override
    public Vehicle findByPlate(String plate) {
        String sql = "SELECT * FROM vehicles WHERE license_plate = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plate);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                            rs.getInt("id"),
                            rs.getString("license_plate"),
                            rs.getString("owner_name"),
                            rs.getString("type")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding vehicle: " + e.getMessage());
        }
        return null;
    }
}