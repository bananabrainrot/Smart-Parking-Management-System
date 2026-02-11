package components.dataaccess.postgres;

import components.dataaccess.api.ReportingGateway;
import edu.aitu.oop3.db.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostgresReportingGateway implements ReportingGateway {
    @Override
    public BigDecimal getRevenueBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT COALESCE(SUM(total_cost), 0) AS total FROM reservations WHERE end_time BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(start));
            pstmt.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating revenue report", e);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public Map<Integer, Long> getPopularStartHours(LocalDateTime start, LocalDateTime end) {
        String sql = """
                SELECT EXTRACT(HOUR FROM start_time) AS hour_bucket, COUNT(*) AS cnt
                FROM reservations
                WHERE start_time BETWEEN ? AND ?
                GROUP BY hour_bucket
                ORDER BY cnt DESC, hour_bucket ASC
                """;
        Map<Integer, Long> result = new LinkedHashMap<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(start));
            pstmt.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getInt("hour_bucket"), rs.getLong("cnt"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating popular hours report", e);
        }
        return result;
    }
}
