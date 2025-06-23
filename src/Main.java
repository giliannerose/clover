import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();

        System.out.println("=== FinMark Login ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean success = authService.login(username, password);

        if (!success) {
            System.out.println("Access denied.");
        } else {
            System.out.println("Access granted. Proceed to dashboard...");
        }

        scanner.close();
    }
}
