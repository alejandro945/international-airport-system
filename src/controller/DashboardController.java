package controller;

import controller.bar.*;
import controller.crud.*;
import controller.crud.AirlineEmployeesController;
import controller.view.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
    private final String[] userTypes = Arrays.toString(UserRole.class.getEnumConstants()).replaceAll("^.|.$", "")
            .split(", ");
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
    private Label notifications;

    @FXML
    private Pane mainBar;
    private Route route;
    private boolean userType;

    public DashboardController(Airport airport, AirportController airportController) {
        this.airportController = airportController;
        this.airport = airport;
    }

    public AirportController geAirportController() {
        return airportController;
    }

    public boolean getUserType() {
        return userType;
    }

    public Route getRoute() {
        return route;
    }

    public void init() {
        if (airport.getLogged() != null) {
            userType = true;
            lblUser.setText(airport.getLogged().getName());
            String path = airport.getLogged().getIconPath();
            notifications.setText(airport.getLogged().getNotifications());
            if (path != null) {
                imgUser.setFill(new ImagePattern(new Image(path)));
            } else {
                imgUser.setFill(new ImagePattern(new Image(Route.USER_ICON.getRoute())));
            }
            devUserType.setValue(airport.getLogged().getRole().name());
        } else {
            userType = false;
            lblUser.setText(airport.getAdminLogged().getName());
            imgUser.setFill(new ImagePattern(new Image(Route.USER_ICON.getRoute())));
            devUserType.setValue(airport.getAdminLogged().getRole().name());
        }
        try {
            changeUserType((ActionEvent) devUserType.getOnAction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        startClock();
        devUserType.getItems().addAll(userTypes);
        devUserType.setOnAction(event -> {
            try {
                changeUserType(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
            validateFlights();
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void validateFlights() {
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("YYYY/dd/MM");
        String dateRender = formatDate.format(date);
        dateRender = dateRender.replaceAll("/", "-");
        String time = lblTime.getText().substring(0, 5);
        for (Flight flight : airport.getFlights()) {
            FlightState state = flight.getFlightStatus();
            if (state == FlightState.SCHEDULED) {
                if (dateRender.equals(flight.getDepartureDate()) && time.compareTo(flight.getDepartureHour()) < 0) {
                    flight.setFlightStatus(FlightState.BOARD);
                } else if (dateRender.equals(flight.getArrivalDate())
                        && time.compareTo(flight.getDepartureHour()) < 0) {
                    flight.setFlightStatus(FlightState.AIRBORNE);
                } else if (dateRender.compareTo(flight.getDepartureDate()) > 0
                        && dateRender.compareTo(flight.getArrivalDate()) < 0) {
                    flight.setFlightStatus(FlightState.AIRBORNE);
                } else if (dateRender.equals(flight.getDepartureDate())
                        && time.compareTo(flight.getDepartureHour()) >= 0) {
                    flight.setFlightStatus(FlightState.AIRBORNE);
                } else if (dateRender.compareTo(flight.getArrivalDate()) > 0
                        && time.compareTo(flight.getArrivalHour()) > 0) {
                    flight.setFlightStatus(FlightState.DONE);
                }
            }
        }
    }

    @FXML
    public void showProfile(MouseEvent event) throws IOException {
        loadView(Route.PROFILE);
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

    public Stage loadModal(Route route, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(route.getRoute()));
        fxmlLoader.setController(controller);
        Parent modal = fxmlLoader.load();
        Scene scene = new Scene(modal);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        return stage;
    }

    public Object setViewController(Route route) {
        this.route = route;
        switch (route) {
            case MY_TRIPS:
                return new FlightsBookedController(airport, this);
            case ACTIVE_FLIGHTS:
                return new ActiveFlightsController(airport, this);
            case UPCOMING_FLIGHTS:
                return new UpcomingFlightsController(this, airport);
            case INDICATORS:
                return new IndicatorsController(this, airport);
            case FLIGHTS:
                return new AirlineFlightsController(this);
            case AIRLINE_EMPLOYEES:
                return new AirlineEmployeesController(airport, this);
            case USER_TABLE:
                return new UserController(airport, this);
            case AIRLINE_TABLE:
                return new AirlineController(airport, this);
            case TRACK_TABLE:
                return new TrackController(airport, this);
            case AIRCRAFT_TABLE:
                return new AircraftController(airport, this);
            case NEW_TRIP:
                return new BookFlightController(airport, this);
            case PROFILE:
                return new ProfileController(this, airport);
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

    public void changeUserType(ActionEvent event) throws IOException {
        String type = devUserType.getValue();
        loadView(Route.HOME);
        switch (type) {
            case ("COSTUMER_USER"):
                loadBar(Route.COSTUMER);
                activeUser = "Client";
                break;
            case ("AIRPORT_ADMIN"):
                loadBar(Route.AIRPORT_ADMIN);
                activeUser = "Airport Admin";
                break;
            case ("TOWER_SUPERVISOR"):
                loadBar(Route.CT_SUPERVISOR);
                activeUser = "CT Supervisor";
                break;
            case ("MIGRATION_AGENT"):
                loadBar(Route.MIGRATION_AGENT);
                activeUser = "Migration Agent";
                break;
            case ("AIRLINE_ADMIN"):
                loadBar(Route.AIRLINE_ADMIN);
                activeUser = "Airline Admin";
                break;
        }
    }

    // --------------------------- Alerts

    public void alert(Route type, String message) {
        airportController.createAlert(message, type);
    }
}
