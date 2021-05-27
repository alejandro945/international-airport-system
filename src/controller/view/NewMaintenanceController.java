package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import route.Route;

import java.io.IOException;

public class NewMaintenanceController {

    private final DashboardController dController;

    public NewMaintenanceController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    private ChoiceBox<?> maintenanceType;

    @FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private TextArea txtObservations;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        dController.loadView(Route.FLIGHTS_STATUS);
    }

    @FXML
    void scheduleMaintenance(ActionEvent event) {

    }
}
