package controller.node;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Flight;
import model.FlightState;

import java.time.LocalDate;
import java.time.LocalTime;

public class NodeUpcomingController {

    @FXML
    private Label error;

    @FXML
    private Label suspense;

    @FXML
    private Label active;

    @FXML
    private JFXTimePicker txtElapsed;

    @FXML
    private JFXTimePicker txtArrival;

    @FXML
    private Label lblDeparture;

    @FXML
    private Label lblDestination;

    @FXML
    private JFXDatePicker dateDeparture;

    @FXML
    private JFXDatePicker dateDestination;

    @FXML
    private Label lblPlane;

    @FXML
    private Label lblAirline;

    @FXML
    private Label txtDuration;

    @FXML
    private Label lblTrack;

    public void getData(Flight flight) {
        initState(flight.getFlightStatus());
        txtElapsed.setValue(LocalTime.parse(flight.getDepartureHour()));
        txtArrival.setValue(LocalTime.parse(flight.getArrivalHour()));
        dateDeparture.setValue(LocalDate.parse(flight.getDepartureDate()));
        dateDestination.setValue(LocalDate.parse(flight.getArrivalDate()));
        lblDeparture.setText(flight.getOrigin().toString());
        lblDestination.setText(flight.getDestination().toString());
        lblAirline.setText(flight.getAirline().toString());
        lblPlane.setText("A385");
        lblTrack.setText("A45");
        long seconds = flight.getDuration();
        long hours = seconds / 3600;
        seconds -= (hours * 3600);
        Long minutes = seconds / 60;
        txtDuration.setText(hours + " hours " + minutes + " minutes");
    }

    public void initState(FlightState state) {
        if (state == FlightState.CANCELED || state == FlightState.DELAYED) {
            error.toFront();
            error.setText(state.toString());
        } else if (state == FlightState.DONE || state == FlightState.AIRBORNE) {
            active.toFront();
            active.setText(state.toString());
        } else {
            suspense.toFront();
            suspense.setText(state.toString());
        }
    }
}
