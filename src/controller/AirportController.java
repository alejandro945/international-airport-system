package controller;

import model.*;
import route.Route;

import java.io.IOException;

import com.jfoenix.controls.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.*;

public class AirportController {
    @FXML
    private Circle btnCloseLogin;

    @FXML
    private Circle btnMinimize;

    @FXML
    private Pane mainPane;

    private LoginController loginController;
    private DashboardController dashboardController;

    public AirportController(Airport airport) {
        loginController = new LoginController(airport, this);
        dashboardController = new DashboardController(airport, this);
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {
        if (event.getSource() == btnCloseLogin) {
            System.exit(0);
        } else if (event.getSource() == btnMinimize) {
            getWindow().setIconified(true);
        }
    }

    public Stage getWindow() {
        return (Stage) mainPane.getScene().getWindow();
    }

    public void showLogin() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(Route.LOGIN.getRoute()));
        fxmlloader.setController(loginController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

    public void showDashboard() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(Route.DASHBOARD.getRoute()));
        fxmlloader.setController(dashboardController);
        Parent root = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        dashboardController.init();
    }

    public void createAlert(String message) {
        JFXAlert<String> newAlert = new JFXAlert<>();
        newAlert.initModality(Modality.APPLICATION_MODAL);
        newAlert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Airport Alerts"));
        layout.setBody(new Label(message));

        JFXButton cancelButton = new JFXButton("OK");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(closeEvent -> newAlert.hideWithAnimation());
        layout.setActions(cancelButton);
        Stage stage = (Stage) newAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:" + Route.ICON.getRoute()));
        newAlert.setContent(layout);
        newAlert.showAndWait();
    }
}
