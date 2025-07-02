
package services;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AuthService {
    private final UserRepository userRepo = new UserRepository();

    public boolean login(String email, String password) {
        User user = userRepo.getUserByEmail(email);

        if (user == null) {
            System.out.println(" User not found.");
            return false;
        }

        if (BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
            System.out.println(" Login successful! Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
            return true;
        } else {
            System.out.println(" Incorrect password.");
            return false;
        }

    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
    }
}
