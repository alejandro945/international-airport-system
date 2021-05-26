package controller;

import controller.bar.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Airport;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private AirportController airportController;
    private Airport airport;

    // For development purposes only.
    @FXML
    private ChoiceBox<String> devUserType;
    private String activeUser;
    private final String[] userTypes = { "Client", "Airport Admin", "CT Supervisor", "Migration Agent",
            "Airline Admin" };
    // Delete before release.

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUser;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane mainBar;

    public DashboardController(Airport airport, AirportController airportController) {
        this.airportController = airportController;
        this.airport = airport;
    }

    public void init() {
        String path = airport.getLogged().getIconPath();
        if (path != null) {
            // userIcon.setImage(new Image(path));
        }
        // lblUserName.setText(airport.getLogged().getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startClock();
        devUserType.getItems().addAll(userTypes);
        devUserType.setOnAction(this::changeUserType);
    }

    public void startClock() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.now();
        lblDate.setText(dtf.format(date));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            if (currentTime.getMinute() < 10 && currentTime.getSecond() < 10) {
                lblTime.setText(
                        currentTime.getHour() + ":0" + currentTime.getMinute() + ":0" + currentTime.getSecond());
            } else if (currentTime.getMinute() < 10) {
                lblTime.setText(currentTime.getHour() + ":0" + currentTime.getMinute() + ":" + currentTime.getSecond());
            } else if (currentTime.getSecond() < 10) {
                lblTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":0" + currentTime.getSecond());
            } else {
                lblTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
            }
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void loadBar(Route route) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(route.getRoute()));
        setBarController(fxmlLoader, route);
        Parent bar = fxmlLoader.load();
        mainBar.getChildren().setAll(bar);
    }

    public void loadView(Route route) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(route.getRoute()));
        Parent view = fxmlLoader.load();
        mainPane.getChildren().setAll(view);
    }

    public void setBarController(FXMLLoader fxmlLoader, Route route) {
        switch (route) {
            case AIRLINE_ADMIN:
                fxmlLoader.setController(new AirlineAdminBarController(this));
                break;
            case AIRPORT_ADMIN:
                fxmlLoader.setController(new AirportAdminBarController(this));
                break;
            case CT_SUPERVISOR:
                fxmlLoader.setController(new CTSupervisorBarController(this));
                break;
            case MIGRATION_AGENT:
                fxmlLoader.setController(new MigrationAgentBarController(this));
                break;
            case COSTUMER:
                fxmlLoader.setController(new ClientBarController(this));
                break;
            default:
                break;
        }
    }

    public void changeUserType(ActionEvent event) {
        String type = devUserType.getValue();
        switch (type) {
            case ("Client"):
                try {
                    loadBar(Route.COSTUMER);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ("Airport Admin"):
                try {
                    loadBar(Route.AIRPORT_ADMIN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ("CT Supervisor"):
                try {
                    loadBar(Route.CT_SUPERVISOR);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ("Migration Agent"):
                try {
                    loadBar(Route.MIGRATION_AGENT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ("Airline Admin"):
                try {
                    loadBar(Route.AIRLINE_ADMIN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
