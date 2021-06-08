package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UpcomingFlightsController {

    @FXML
    private TextField txtSearch;

    private final DashboardController dController;

    public UpcomingFlightsController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    public void searchFlight(ActionEvent event) {

    }

    public DashboardController getdController() {
        return dController;
    }

    

}
