import at.favre.lib.crypto.bcrypt.BCrypt;
import services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertSampleUsers {
    public static void main(String[] args) {
        String[] usernames = { "Admin User", "Finance Manager", "Marketing Lead", "Analyst One" };
        String[] emails = { "admin@gmail.com", "finance@gmail.com", "marketing@gmail.com",
                "analyst@gmail.com" };
        String[] roles = { "admin", "finance", "marketing", "analyst" };
        String rawPassword = "admin123";

        String hashedPassword = BCrypt.withDefaults().hashToString(10, rawPassword.toCharArray());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < usernames.length; i++) {
                stmt.setString(1, usernames[i]);
                stmt.setString(2, emails[i]);
                stmt.setString(3, hashedPassword);
                stmt.setString(4, roles[i]);
                stmt.executeUpdate();
            }

            System.out.println("✅ Sample users inserted successfully.");
        } catch (Exception e) {
            System.out.println("❌ Failed to insert users.");
            e.printStackTrace();
        }
    }
}
