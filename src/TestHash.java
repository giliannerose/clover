import at.favre.lib.crypto.bcrypt.BCrypt;

public class TestHash {
    public static void main(String[] args) {
        String password = "admin123";
        String hashed = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        System.out.println("Hashed password: " + hashed);
    }
}

