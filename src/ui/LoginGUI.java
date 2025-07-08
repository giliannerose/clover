package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import services.AuthService;
import services.FaultyUserRepository;
import services.User;
import services.UserRepository;

public class LoginGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Finmark Login");

        // Root container
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #1e1e2f;");

        // Compact square-like login card
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setMaxWidth(320);
        card.setStyle("-fx-background-color: #2a2a40; -fx-background-radius: 12;");
        card.setEffect(new DropShadow(10, Color.BLACK));

        Label title = new Label("Finmark");
        title.setFont(Font.font("Arial", 24));
        title.setTextFill(Color.WHITE);

        Label userLabel = new Label("Email:");
        userLabel.setTextFill(Color.LIGHTGRAY);
        TextField userField = new TextField();
        userField.setPromptText("Enter your email");
        userField.setStyle(
                "-fx-background-color: #3b3b52; -fx-text-fill: white; -fx-prompt-text-fill: #bbbbbb; -fx-background-radius: 6;");
        userField.setMaxWidth(Double.MAX_VALUE);

        Label passLabel = new Label("Password:");
        passLabel.setTextFill(Color.LIGHTGRAY);
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");
        passField.setStyle(
                "-fx-background-color: #3b3b52; -fx-text-fill: white; -fx-prompt-text-fill: #bbbbbb; -fx-background-radius: 6;");
        passField.setMaxWidth(Double.MAX_VALUE);

        Label resultLabel = new Label();
        resultLabel.setTextFill(Color.web("#ff4c4c"));
        resultLabel.setStyle("-fx-font-size: 12;");
        resultLabel.setWrapText(true);
        resultLabel.setVisible(false);

        Button loginButton = new Button("Login");
        loginButton.setStyle(
                "-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 6;");
        loginButton.setMaxWidth(Double.MAX_VALUE);

        loginButton.setOnAction(e -> {
            String email = userField.getText().trim();
            String password = passField.getText();

            try {
                if (email.isEmpty() && password.isEmpty()) {
                    resultLabel.setText("❌ Email and password are required.");
                    resultLabel.setVisible(true);
                    return;
                } else if (email.isEmpty()) {
                    resultLabel.setText("❌ Email is required.");
                    resultLabel.setVisible(true);
                    return;
                } else if (password.isEmpty()) {
                    resultLabel.setText("❌ Password is required.");
                    resultLabel.setVisible(true);
                    return;
                }

                if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    resultLabel.setText("❌ Invalid email format.");
                    resultLabel.setVisible(true);
                    return;
                }

                UserRepository repo = new UserRepository();
                User user = repo.getUserByEmail(email);

                if (user == null) {
                    resultLabel.setText("❌ Incorrect email or password.");
                    resultLabel.setVisible(true);
                } else {
                    boolean success = AuthService.verifyPassword(password, user.getPassword());
                    if (success) {
                        resultLabel.setText("✅ Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
                        resultLabel.setTextFill(Color.LIGHTGREEN);
                        resultLabel.setVisible(true);

                        // Close login and show dashboard
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.close();
                        DashboardGUI.show(user.getUsername(), user.getRole());
                    } else {
                        resultLabel.setText("❌ Incorrect email or password.");
                        resultLabel.setTextFill(Color.web("#ff4c4c"));
                        resultLabel.setVisible(true);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace(); // for debugging
                resultLabel.setText("❌ Unexpected system error. Please try again.");
                resultLabel.setVisible(true);
            }
        });

        // Add to card
        card.getChildren().addAll(title, userLabel, userField, passLabel, passField, resultLabel, loginButton);
        root.getChildren().add(card);

        Scene scene = new Scene(root, 500, 400); // More compact window
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void show() {
        LoginGUI app = new LoginGUI();
        Stage stage = new Stage();
        app.start(stage);
    }
}
