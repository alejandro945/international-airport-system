package controller.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Trip;

public class FlightCardController {

    @FXML
    private Label lblDeparture;

    @FXML
    private Label lblDestination;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;
    private Trip flight;

    public FlightCardController(Trip flight) {
        this.flight = flight;
    }

    public void setData() {
        lblDeparture.setText(String.valueOf(flight.getTicket().getFlight().getOrigin()));
        lblDestination.setText(String.valueOf(flight.getTicket().getFlight().getDestination()));
        lblDate.setText("11 Junio 2021");
        lblTime.setText("8:00");
    }

}
