package controller.bar;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AirlineAdminBarController implements Initializable {

    private final DashboardController dController;

    public AirlineAdminBarController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    private ImageView imgAirlineFlights;

    @FXML
    private ImageView imgEmployees;
    @FXML
    private ImageView imgAircrafts;
    @FXML
    private ImageView imgHome;

    @FXML
    void showHome(MouseEvent event) throws IOException {
        resetAllIcons();
        imgHome.setImage(new Image("/ui/assets/img/icons/active-dashboard.png"));
        dController.loadView(Route.HOME);
    }

    @FXML
    void showAircrafts(MouseEvent event) throws IOException {
        resetAllIcons();
        imgAircrafts.setImage(new Image("/ui/assets/img/icons/active-flights.png"));
        dController.loadView(Route.AIRCRAFT_TABLE);
    }

    @FXML
    void showFlights(MouseEvent event) throws IOException {
        resetAllIcons();
        imgAirlineFlights.setImage(new Image("/ui/assets/img/icons/active-plane-flat.png"));
        dController.loadView(Route.FLIGHTS);
    }

    @FXML
    void showEmployees(MouseEvent event) throws IOException {
        resetAllIcons();
        imgEmployees.setImage(new Image("/ui/assets/img/icons/active-employees.png"));
        dController.loadView(Route.AIRLINE_EMPLOYEES);
    }

    public void resetAllIcons() {
        imgHome.setImage(new Image("/ui/assets/img/icons/dashboard.png"));
        imgAircrafts.setImage(new Image("/ui/assets/img/icons/flights.png"));
        imgAirlineFlights.setImage(new Image("/ui/assets/img/icons/plane-flat.png"));
        imgEmployees.setImage(new Image("/ui/assets/img/icons/employees.png"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetAllIcons();
        imgHome.setImage(new Image("/ui/assets/img/icons/active-dashboard.png"));
    }

}
