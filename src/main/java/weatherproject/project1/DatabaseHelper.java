package weatherproject.project1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/weather_db"; // Change as necessary
    private static final String USER = "root"; // Change as necessary
    private static final String PASSWORD = "password"; // Change as necessary

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void insertWeatherSummary(String city, double avgTemp, double minTemp, double maxTemp, String maxCondition, String date) {
        String query = "INSERT INTO daily_weather_summary (city, avg_temp, min_temp, max_temp, max_condition, date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, city);
            pstmt.setDouble(2, avgTemp);
            pstmt.setDouble(3, minTemp);
            pstmt.setDouble(4, maxTemp);
            pstmt.setString(5, maxCondition);
            pstmt.setString(6, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
