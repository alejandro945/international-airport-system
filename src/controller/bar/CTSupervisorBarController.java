package controller.bar;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import route.Route;

import java.io.IOException;

public class CTSupervisorBarController {
    @FXML
    private ImageView imgActiveFlights;

    @FXML
    private ImageView imgUpcomingFlights;

    @FXML
    private ImageView imgStatus;

    private final DashboardController dController;

    public CTSupervisorBarController(DashboardController dController) {
        this.dController = dController;
    }
    @FXML
    private ImageView imgHome;
    
    @FXML
    void showHome(MouseEvent event) throws IOException {
        resetAllIcons();
        imgHome.setImage(new Image("/ui/assets/img/icons/active-dashboard.png"));
        dController.loadView(Route.HOME);
    }

    @FXML
    void showActiveFlights(MouseEvent event) throws IOException {
        resetAllIcons();
        imgActiveFlights.setImage(new Image("/ui/assets/img/icons/active-flights.png"));
        dController.loadView(Route.ACTIVE_FLIGHTS);
    }

    @FXML
    void showUpcomingFlights(MouseEvent event) throws IOException {
        resetAllIcons();
        imgUpcomingFlights.setImage(new Image("/ui/assets/img/icons/active-upcoming-flights.png"));
        dController.loadView(Route.UPCOMING_FLIGHTS);
    }

    @FXML
    void showStatus(MouseEvent event) throws IOException {
        resetAllIcons();
        imgStatus.setImage(new Image("/ui/assets/img/icons/active-status.png"));
        dController.loadView(Route.TRACK_TABLE);
    }

    public void resetAllIcons() {
        imgHome.setImage(new Image("/ui/assets/img/icons/tickets-flat.png"));
        imgActiveFlights.setImage(new Image("/ui/assets/img/icons/flights.png"));
        imgUpcomingFlights.setImage(new Image("/ui/assets/img/icons/upcoming-flights.png"));
        imgStatus.setImage(new Image("/ui/assets/img/icons/status.png"));
    }

}
