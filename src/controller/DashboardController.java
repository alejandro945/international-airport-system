package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Airport;

public class DashboardController {
    @FXML
    private Label lblUserName;

    @FXML
    private ImageView userIcon;
    private AirportController airportController;
    private Airport airport;

    public DashboardController(Airport airport, AirportController airportController) {
        this.airportController = airportController;
        this.airport = airport;
    }

    public void init() {
        String path = airport.getLogged().getIconPath();
        if (path != null) {
            userIcon.setImage(new Image(path));
        }
        lblUserName.setText(airport.getLogged().getName());
    }
}
