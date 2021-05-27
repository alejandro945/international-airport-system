package controller.bar;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import route.Route;

import java.io.IOException;

public class AirlineAdminBarController {

    private final DashboardController dController;

    public AirlineAdminBarController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    private ImageView imgAirlineFlights;

    @FXML
    private ImageView imgEmployees;

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
        imgAirlineFlights.setImage(new Image("/ui/assets/img/icons/plane-flat.png"));
        imgEmployees.setImage(new Image("/ui/assets/img/icons/employees.png"));
    }

}
