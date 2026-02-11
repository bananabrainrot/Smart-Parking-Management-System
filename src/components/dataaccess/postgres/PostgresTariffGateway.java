package components.dataaccess.postgres;

import components.dataaccess.api.TariffGateway;
import edu.aitu.oop3.db.DatabaseConnection;
import entities.Tariff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PostgresTariffGateway implements TariffGateway {
    @Override
    public Optional<Tariff> findById(int id) {
        String sql = "SELECT * FROM tariffs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Tariff(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getBigDecimal("rate_per_hour")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding tariff", e);
        }
        return Optional.empty();
    }
}
