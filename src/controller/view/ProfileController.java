package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfileController {

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPasswordConfirm;

    private DashboardController dController;

    public ProfileController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void closeAccount(ActionEvent event) {

    }

    @FXML
    void saveData(ActionEvent event) {

    }

}
