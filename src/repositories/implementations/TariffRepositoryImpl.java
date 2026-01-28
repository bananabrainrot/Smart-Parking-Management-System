package repositories.implementations;

import repositories.ITariffRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TariffRepositoryImpl implements ITariffRepository {
    private final IDB db;
    public TariffRepositoryImpl(IDB db) { this.db = db; }

    @Override
    public double getRateByName(String name) {
        String sql = "SELECT rate_per_hour FROM tariffs WHERE name = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getDouble("rate_per_hour");
        } catch (Exception e) { e.printStackTrace(); }
        return 10.0; // Дефолтная цена, если в базе пусто
    }
}