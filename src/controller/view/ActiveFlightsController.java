package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ActiveFlightsController {

    @FXML
    private TextField txtSearch;

    private final DashboardController dController;

    public ActiveFlightsController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void searchFlight(ActionEvent event) {

    }

}
