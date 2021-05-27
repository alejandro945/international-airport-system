package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import route.Route;

import java.io.IOException;

public class AirlineEmployeesController {

    @FXML
    private TextField txtSearch;

    private final DashboardController dController;

    public AirlineEmployeesController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void searchEmployee(ActionEvent event) {

    }

    @FXML
    void showNewEmployee(ActionEvent event) throws IOException {
        dController.loadView(Route.NEW_EMPLOYEE);
    }

}
