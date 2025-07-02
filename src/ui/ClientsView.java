package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class ClientsView {
    public static StackPane getView() {
        StackPane pane = new StackPane();
        pane.getChildren().add(new Label("👥 Clients Page"));
        return pane;
    }
}
