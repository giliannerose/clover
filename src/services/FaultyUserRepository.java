package services;

import java.sql.SQLException;

public class FaultyUserRepository extends UserRepository {

    @Override
    public User getUserByEmail(String email) {
        // Fault 1: Simulate DB failure
        if (email.equals("crash@test.com")) {
            throw new RuntimeException("Simulated database failure");
        }

        // Fault 2: Simulate null username
        if (email.equals("nulluser@test.com")) {
            User user = new User();
            user.setEmail(email);
            user.setUsername(null); // Simulated missing data
            user.setRole("Admin");
            user.setPassword("$2a$10$dummyhashedpassword");
            return user;
        }

        // Fault 3: Invalid password hash
        if (email.equals("badhash@test.com")) {
            User user = new User();
            user.setEmail(email);
            user.setUsername("TestUser");
            user.setRole("User");
            user.setPassword("invalid-bcrypt-hash"); // Simulated corruption
            return user;
        }

        // Fallback to actual DB query
        return super.getUserByEmail(email);
    }
}
