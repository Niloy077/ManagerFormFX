package DataBase;

import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:contacts.db"; // SQLite file

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
                "id TEXT PRIMARY KEY," +
                "firstName TEXT," +
                "lastName TEXT," +
                "email TEXT" +
                ")";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

