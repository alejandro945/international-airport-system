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

    @FXML
    private ImageView imgNewMaintenance;
    private final DashboardController dController;

    public CTSupervisorBarController(DashboardController dController) {
        this.dController = dController;
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

    @FXML
    void showNewMaintenance(MouseEvent event) throws IOException {
        resetAllIcons();
        imgNewMaintenance.setImage(new Image("/ui/assets/img/icons/active-maintenance.png"));
        dController.loadView(Route.NEW_MAINTENANCE);
    }

    public void resetAllIcons() {
        imgActiveFlights.setImage(new Image("/ui/assets/img/icons/flights.png"));
        imgUpcomingFlights.setImage(new Image("/ui/assets/img/icons/upcoming-flights.png"));
        imgStatus.setImage(new Image("/ui/assets/img/icons/status.png"));
        imgNewMaintenance.setImage(new Image("/ui/assets/img/icons/maintenance.png"));
    }

}
