package repositories.implementations;

import edu.aitu.oop3.db.*;
import entities.Vehicle;
import repositories.IVehicleRepository;
import java.sql.*;

public class VehicleRepositoryImpl implements IVehicleRepository {
    private final DatabaseConnection db;

    public VehicleRepositoryImpl(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Vehicle findByPlate(String plate) {
        String sql = "SELECT * FROM vehicles WHERE plate_number = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Vehicle(rs.getInt("id"), rs.getString("plate_number"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (plate_number) VALUES (?)";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehicle.getPlateNumber());
            pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
