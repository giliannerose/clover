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
            String email = userField.getText();
            String password = passField.getText();

            UserRepository repo = new UserRepository();
            User user = repo.getUserByEmail(email);

            if (user == null) {
                resultLabel.setText(" User not found.");
            } else {
                boolean success = AuthService.verifyPassword(password, user.getPassword());
                if (success) {
                    resultLabel.setText(" Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
                } else {
                    resultLabel.setText("Incorrect password.");
                }
            }
        });

        // Scene
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
