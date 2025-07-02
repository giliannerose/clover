package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import services.DashboardService;
import java.util.Map;

public class DashboardGUI {
    public static void show(String username, String role) {
        Stage stage = new Stage();
        stage.setTitle("FinMark Dashboard");
        // Sidebar with navigation buttons
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2C3E50;");
        sidebar.setPrefWidth(200);
        Button dashboardBtn = new Button("Dashboard");
        Button reportsBtn = new Button("Reports");
        Button clientsBtn = new Button("Clients");
        Button userMgmtBtn = new Button("User Management");
        Button logoutBtn = new Button("Logout");
        for (Button btn : new Button[] { dashboardBtn, reportsBtn, clientsBtn, userMgmtBtn, logoutBtn }) {
            btn.setPrefWidth(160);
            btn.setStyle("-fx-background-color: #34495E; -fx-text-fill: white;");
        }
        sidebar.getChildren().addAll(dashboardBtn, reportsBtn, clientsBtn, userMgmtBtn, logoutBtn);
        // Dashboard welcome + metric cards
        Label welcomeLabel = new Label("Welcome, " + username + " (" + role + ")");
        welcomeLabel.setFont(Font.font(18));
        welcomeLabel.setPadding(new Insets(10, 0, 20, 0));
        Map<String, String> metrics = DashboardService.getDashboardMetrics();
        HBox cardsContainer = new HBox(20);
        cardsContainer.setPadding(new Insets(10));
        cardsContainer.setAlignment(Pos.CENTER);
        VBox revenueCard = createCard("Total Revenue", metrics.get("Total Revenue"));
        VBox expensesCard = createCard("Total Expenses", metrics.get("Total Expenses"));
        VBox profitCard = createCard("Net Profit", metrics.get("Net Profit"));
        VBox clientsCard = createCard("Active Clients", metrics.get("Active Clients"));
        cardsContainer.getChildren().addAll(revenueCard, expensesCard, profitCard, clientsCard);
        VBox dashboardContent = new VBox();
        dashboardContent.setPadding(new Insets(20));
        dashboardContent.getChildren().addAll(welcomeLabel, cardsContainer);
        // Center view container
        BorderPane mainContent = new BorderPane();
        mainContent.setPadding(new Insets(20));
        mainContent.setCenter(dashboardContent); // default view
        // Actions for navigation
        dashboardBtn.setOnAction(e -> mainContent.setCenter(dashboardContent));
        reportsBtn.setOnAction(e -> mainContent.setCenter(ReportsView.getView()));
        clientsBtn.setOnAction(e -> mainContent.setCenter(ClientsView.getView()));
        userMgmtBtn.setOnAction(e -> mainContent.setCenter(UserManagementView.getView()));
        logoutBtn.setOnAction(e -> {
            stage.close();
            LoginGUI.show(); // assumes you have LoginGUI.show() method
        });
        // Root layout
        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(mainContent);
        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.show();
    }

    private static VBox createCard(String title, String value) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font(14));
        titleLabel.setTextFill(Color.GRAY);
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", 20));
        valueLabel.setTextFill(Color.DARKBLUE);
        VBox box = new VBox(10, titleLabel, valueLabel);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle(
                "-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;");
        box.setPrefSize(180, 100);
        return box;
    }
}
