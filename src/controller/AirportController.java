package controller;

import model.*;
import route.Route;
import controller.login.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jfoenix.controls.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.*;

public class AirportController implements Serializable {
    private final String SAVE_PATH_FILE = "data/Airport.data";
    @FXML
    private Circle btnCloseLogin;

    @FXML
    private Circle btnMinimize;

    @FXML
    private Pane mainPane;

    private LoginController loginController;
    private DashboardController dashboardController;
    private Airport airport;

    public AirportController(Airport airport) {
        this.airport = airport;
        loginController = new LoginController(airport, this);
        dashboardController = new DashboardController(airport, this);
        dateRender();
    }

    public void dateRender() {
        File file = new File(SAVE_PATH_FILE);
        if (file.length() > 0) {
            loadData();
        }
    }

    public void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE)));
            airport = (Airport) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
            oos.writeObject(airport);
            oos.close();
        } catch (FileNotFoundException e) {
            createAlert(Constant.FILE_NOT_FOUND, Route.ERROR);
        } catch (IOException e) {
            createAlert(Constant.IOEXCEPTION, Route.ERROR);
        }
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
