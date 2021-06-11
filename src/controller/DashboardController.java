package controller;

import controller.bar.*;
import controller.crud.*;
import controller.view.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Airport;
import model.Flight;
import model.FlightState;
import model.UserRole;
import route.Route;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private AirportController airportController;
    private Airport airport;
    private int activeUser;
    private String type;

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
    @FXML
    private ImageView airlineLogo;

    private Route route;

    public DashboardController(Airport airport, AirportController airportController) {
        this.airportController = airportController;
        this.airport = airport;
    }

    public AirportController geAirportController() {
        return airportController;
    }

    public Route getRoute() {
        return route;
    }

    public void init() {
        if (airport.getLogged() != null) {
            activeUser = 1;
            lblUser.setText(airport.getLogged().getName());
            String path = airport.getLogged().getIconPath();
            notifications.setText(airport.getLogged().getNotifications());
            if (path != null) {
                imgUser.setFill(new ImagePattern(new Image(path)));
            } else {
                imgUser.setFill(new ImagePattern(new Image(Route.USER_ICON.getRoute())));
            }
            type = airport.getLogged().getRole().name();
        } else if (airport.getAirlineLogged() != null) {
            activeUser = 2;
            lblUser.setText(airport.getAirlineLogged().getName());
            File file = new File(airport.getAirlineLogged().getAirline().getIcon());
            airlineLogo.setImage(new Image("file:///" + file.getAbsolutePath()));
            imgUser.setFill(new ImagePattern(new Image(Route.USER_ICON.getRoute())));
            type = airport.getAirlineLogged().getRole().name();
        } else {
            activeUser = 3;
            lblUser.setText(airport.getAdminLogged().getName());
            imgUser.setFill(new ImagePattern(new Image(Route.USER_ICON.getRoute())));
            type = airport.getAdminLogged().getRole().name();
            if (airport.getAdminLogged().getRole() == UserRole.MIGRATION_AGENT) {
                renderToast();
            }
        }
        try {
            renderUser();
        } catch (IOException e) {
            alert(Route.WARNING, Constant.IOEXCEPTION);
        }
    }

    public void renderToast() {
        notifications.setText("Costos del aeropuerto por persona $ " + airport.getExpenses());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        startClock();
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
        SimpleDateFormat formatDate = new SimpleDateFormat("YYYY/MM/dd");
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

    @FXML
    public void logout(MouseEvent event) throws IOException, InterruptedException {
       closeAccount();
    }

    public void closeAccount() throws IOException, InterruptedException{
        airport.saveData();
        airport.loadData();
        airport.setLogged(null);
        airport.setAdminLogged(null);
        airport.setAirlineLogged(null);
        airlineLogo.setImage(null);
        notifications.setText("");
        geAirportController().renderScreen(Route.LOGIN);
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
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
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
                return new FlightController(airport, this, airport.getAirlineLogged().getAirline());
            case AIRLINE_EMPLOYEES:
                return new AirlineEmployeesController(airport, this, airport.getAirlineLogged().getAirline());
            case USER_TABLE:
                return new UserController(airport, this);
            case AIRLINE_TABLE:
                return new AirlineController(airport, this);
            case TRACK_TABLE:
                return new TrackController(airport, this);
            case AIRCRAFT_TABLE:
                return new AircraftController(airport, this, airport.getAirlineLogged().getAirline());
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

    public int getActiveUser() {
        return activeUser;
    }

    public void renderUser() throws IOException {
        loadView(Route.HOME);
        switch (type) {
            case ("COSTUMER_USER"):
                loadBar(Route.COSTUMER);
                break;
            case ("AIRPORT_ADMIN"):
                loadBar(Route.AIRPORT_ADMIN);
                break;
            case ("TOWER_SUPERVISOR"):
                loadBar(Route.CT_SUPERVISOR);
                break;
            case ("MIGRATION_AGENT"):
                loadBar(Route.MIGRATION_AGENT);
                break;
            case ("AIRLINE_ADMIN"):
                loadBar(Route.AIRLINE_ADMIN);
                break;
        }
    }

    // --------------------------- Alerts

    public void alert(Route type, String message) {
        airportController.createAlert(message, type);
    }
}
