package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class UserManagementView {
    public static StackPane getView() {
        StackPane pane = new StackPane();
        pane.getChildren().add(new Label("👤 User Management Page"));
        return pane;
    }
}
