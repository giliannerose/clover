import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FinMark Login");

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // UI Components
        Label userLabel = new Label("Email:");
        TextField userField = new TextField();

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        Button loginButton = new Button("Login");
        Label resultLabel = new Label();

        // Add components to grid
        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(resultLabel, 1, 3);

        // Button logic
        loginButton.setOnAction(e -> {
            String email = userField.getText().trim();
            String password = passField.getText();

            // Validation: empty input
            if (email.isEmpty() && password.isEmpty()) {
                resultLabel.setText("❌ Email and password are required.");
                return;
            } else if (email.isEmpty()) {
                resultLabel.setText("❌ Email is required.");
                return;
            } else if (password.isEmpty()) {
                resultLabel.setText("❌ Password is required.");
                return;
            }

            // Validation: simple email format
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                resultLabel.setText("❌ Invalid email format.");
                return;
            }

            UserRepository repo = new UserRepository();
            User user = repo.getUserByEmail(email);

            if (user == null) {
                resultLabel.setText("❌ Incorrect email or password.");
            } else {
                boolean success = AuthService.verifyPassword(password, user.getPassword());
                if (success) {
                    resultLabel.setText("✅ Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
                } else {
                    resultLabel.setText("❌ Incorrect email or password.");
                }
            }
        });

        // Scene
        Scene scene = new Scene(grid, 350, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
