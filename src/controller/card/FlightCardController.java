package controller.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Flight;

public class FlightCardController {

    @FXML
    private Label lblDeparture;

    @FXML
    private Label lblDestination;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;
    private Flight flight;

    public FlightCardController(Flight fligth) {
        this.flight = fligth;
    }

    public void setData() {
        lblDeparture.setText("España");
        lblDestination.setText("China");
        lblDate.setText("11 Junio 2021");
        lblTime.setText("8:00");
    }

}
