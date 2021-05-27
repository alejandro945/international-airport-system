package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StatusController {

    @FXML
    private TextField txtSearch;

    private final DashboardController dController;

    public StatusController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void search(ActionEvent event) {

    }

}
