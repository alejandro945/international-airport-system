package controller;

import model.*;
import route.Route;
import thread.Spinner;

import java.io.IOException;

import com.jfoenix.controls.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Route recentRoute;

    public AirportController(Airport airport) {
        loginController = new LoginController(airport, this);
        dashboardController = new DashboardController(airport, this);
    }

    /**
     * @param route
     */
    private void setRecentRoute(Route route) {
        recentRoute = route;
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
     * @throws IOException
     */
    public void showSpinner() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(Route.SPINNER.getRoute()));
        Parent spinner = fxmlloader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(spinner);
    }

    /**
     * @param route
     * @throws IOException
     */
    public void changeScreen(Route route) throws IOException {
        showSpinner();
        Spinner spinner = new Spinner(route, this);
        spinner.start();
    }

    /**
     * @param route
     * @throws IOException
     */
    public void renderScreen(Route route) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(route.getRoute()));
        fxmlloader.setController(setController(route));
        Parent root = fxmlloader.load();
        setRecentRoute(route);
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
        initScreen();
    }

    /**
     * @throws IOException
     */
    public void initScreen() throws IOException {
        if (recentRoute == Route.DASHBOARD) {
            dashboardController.init();
        }
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
