package controller;

import controller.bar.*;
import controller.view.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Airport;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private Circle imgUser;
    @FXML
    private Label lblUser;

    @FXML
    private Pane dashPane;

    @FXML
    private Pane mainBar;

    public DashboardController(Airport airport, AirportController airportController) {
        this.airportController = airportController;
        this.airport = airport;
    }

    public void init() {
        String path = airport.getLogged().getIconPath();
        if (path != null) {
            imgUser.setFill(new ImagePattern(new Image(path)));
        }
        lblUser.setText(airport.getLogged().getName());
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
        fxmlLoader.setController(setBarController(route));
        Parent bar = fxmlLoader.load();
        mainBar.getChildren().setAll(bar);
    }

    public void loadView(Route route) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(route.getRoute()));
        fxmlLoader.setController(setViewController(route));
        Parent view = fxmlLoader.load();
        dashPane.getChildren().setAll(view);
    }

    public Object setViewController(Route route) {
        switch (route) {
            case MY_TRIPS:
                return new ClientFlightsController(this);
            case AP_EMPLOYEES:
                return new AirportEmployeesController(this);
            case NEW_EMPLOYEE:
                return new NewEmployeeController(this);
            case AIRLINES:
                return new AirportAirlinesController(this);
            case NEW_AIRLINE:
                return new NewAirlineController(this);
            case ACTIVE_FLIGHTS:
                return new ActiveFlightsController(this);
            case UPCOMING_FLIGHTS:
                return new UpcomingFlightsController(this);
            case FLIGHTS_STATUS:
                return new StatusController(this);
            case NEW_MAINTENANCE:
                return new NewMaintenanceController(this);
            case INDICATORS:
                return new IndicatorsController(this);
            case FLIGHTS:
                return new AirlineFlightsController(this);
            case AIRLINE_EMPLOYEES:
                return new AirlineEmployeesController(this);
            case MIGRATION_AGENT:
                return new NewMaintenanceController(this);
            default:
                return null;
        }
    }

    public Object setBarController(Route route) {
        switch (route) {
            case AIRLINE_ADMIN:
                return new AirlineAdminBarController(this);
            case AIRPORT_ADMIN:
                return new AirportAdminBarController(this);
            case CT_SUPERVISOR:
                return new CTSupervisorBarController(this);
            case MIGRATION_AGENT:
                return new MigrationAgentBarController(this);
            case COSTUMER:
                return new ClientBarController(this);
            default:
                return null;
        }
    }

    public String getActiveUser() {
        return activeUser;
    }

    public void changeUserType(ActionEvent event) {
        String type = devUserType.getValue();
        switch (type) {
            case ("Client"):
                try {
                    loadBar(Route.COSTUMER);
                    activeUser = "Client";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ("Airport Admin"):
                try {
                    loadBar(Route.AIRPORT_ADMIN);
                    activeUser = "Airport Admin";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ("CT Supervisor"):
                try {
                    loadBar(Route.CT_SUPERVISOR);
                    activeUser = "CT Supervisor";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ("Migration Agent"):
                try {
                    loadBar(Route.MIGRATION_AGENT);
                    activeUser = "Migration Agent";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ("Airline Admin"):
                try {
                    loadBar(Route.AIRLINE_ADMIN);
                    activeUser = "Airline Admin";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
