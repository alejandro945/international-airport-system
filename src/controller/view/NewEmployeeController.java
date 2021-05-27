package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import route.Route;

import java.io.IOException;

public class NewEmployeeController {

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtId;

    @FXML
    private ChoiceBox<?> employeeType;

    private final DashboardController dController;

    public NewEmployeeController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void addEmployee(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        switch (dController.getActiveUser()) {
            case ("Airport Admin"):
                dController.loadView(Route.AP_EMPLOYEES);
                break;
            case ("Airline Admin"):
                dController.loadView(Route.AIRLINE_EMPLOYEES);
                break;
        }
    }

}
