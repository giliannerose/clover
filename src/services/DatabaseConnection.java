
package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/finmark_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Putangina27&";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // public static void main(String[] args) {
    // try {
    // Connection conn = getConnection();
    // System.out.println("Connected to PostgreSQL!");
    // conn.close();
    // } catch (SQLException e) {
    // System.out.println("Connection failed: " + e.getMessage());
    // }
    // }

}
