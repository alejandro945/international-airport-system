package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class AirlineFlightsController {

    @FXML
    private TextField txtSearch;

    private final DashboardController dController;

    public AirlineFlightsController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void importFlights(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");
        File file = fileChooser.showOpenDialog(txtSearch.getScene().getWindow());
    }

    @FXML
    void searchFlight(ActionEvent event) {

    }

}
