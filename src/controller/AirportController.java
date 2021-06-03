package controller;

import model.*;
import route.Route;
import ui.Splash;
import controller.login.*;

import java.io.IOException;

import com.jfoenix.controls.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.*;
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

    /**
     * @param event
     */
    @FXML
    public void handleMouseClick(MouseEvent event) {
        if (event.getSource() == btnCloseLogin) {
            System.exit(0);
        } else if (event.getSource() == btnMinimize) {
            getWindow().setIconified(true);
        }
    }

    /**
     * @return Stage
     */
    public Stage getWindow() {
        return (Stage) mainPane.getScene().getWindow();
    }

    /**
     * @param route
     * @throws IOException
     * @throws InterruptedException
     */
    public void renderScreen(Route route) throws IOException, InterruptedException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(route.getRoute()));
        fxmlloader.setController(setController(route));
        Parent root = fxmlloader.load();
        mainPane.getChildren().setAll(root);
    }

    /**
     * @param route
     * @return Object
     */
    public Object setController(Route route) {
        switch (route) {
            case DASHBOARD:
                return dashboardController;
            case LOGIN:
                return loginController;
            default:
                return null;
        }
    }

    /**
     * Creates airport system alerts
     * 
     * @param message
     * @param alertType
     */
    public void createAlert(String message, Route alertType) {
        JFXAlert<String> newAlert = new JFXAlert<>();
        newAlert.initModality(Modality.APPLICATION_MODAL);
        newAlert.setOverlayClose(false);
        // GIF RENDERING
        ImageView gif = new ImageView(new Image(alertType.getRoute()));
        gif.setFitHeight(50);
        gif.setFitWidth(75);
        // ALERT-CONTENT
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.getStylesheets().add(Route.ALERT.getRoute());
        layout.setHeading(new Label("Airport Alerts"));
        layout.setBody(new HBox(8, new Label(message), gif));
        // ACTIONS
        JFXButton cancelButton = new JFXButton("OK");
        cancelButton.setButtonType(JFXButton.ButtonType.RAISED);
        cancelButton.setCancelButton(true);
        cancelButton.setStyle(Route.ALERT.getRoute());
        cancelButton.setOnAction(closeEvent -> newAlert.hideWithAnimation());
        layout.setActions(cancelButton);
        // STANGING
        Stage stage = (Stage) newAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:" + Route.ICON.getRoute()));
        newAlert.setContent(layout);
        newAlert.showAndWait();
    }
}
