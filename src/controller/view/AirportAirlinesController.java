package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import route.Route;

import java.io.IOException;

public class AirportAirlinesController {

    @FXML
    private TextField txtSearch;

    private final DashboardController dController;

    public AirportAirlinesController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void searchAirline(ActionEvent event) {

    }

    @FXML
    void showNewAirline(ActionEvent event) throws IOException {
        dController.loadView(Route.NEW_AIRLINE);
    }

}
