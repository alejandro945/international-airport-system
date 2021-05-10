package controller;

import model.*;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AirportController {
    @FXML
    private Circle btnCloseLogin;

    @FXML
    private Circle btnMinimize;

    @FXML
    private Pane mainPane;

    private LoginController loginController;

    @FXML
    void handleMouseClick(MouseEvent event) {
        if (event.getSource() == btnCloseLogin) {
            System.exit(0);
        } else if (event.getSource() == btnMinimize) {
            getWindow().setIconified(true);
        }
    }

    public AirportController(Airport airport) {
        loginController = new LoginController(airport);
    }

    public Stage getWindow() {
        return (Stage) mainPane.getScene().getWindow();
    }

    public void showHome() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ui/screens/login.fxml"));
        fxmlloader.setController(loginController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }
}
