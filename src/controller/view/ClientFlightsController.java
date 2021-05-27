package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import route.Route;

import java.io.IOException;

public class ClientFlightsController {

    private final DashboardController dController;

    public ClientFlightsController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void showNewFlight(ActionEvent event) throws IOException {
        dController.loadView(Route.NEW_TRIP);
    }

}
