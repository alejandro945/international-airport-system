package controller.node;

import java.time.LocalDate;
import java.time.LocalTime;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTimePicker;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Flight;
import model.FlightState;
import thread.FlightThread;

public class NodeFlightController {
    @FXML
    private Label txtDuration;

    @FXML
    private Label error;

    @FXML
    private Label suspense;

    @FXML
    private Label active;

    @FXML
    private JFXTimePicker txtElapsed;
    @FXML
    private ImageView warning;
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
    private JFXProgressBar barProgress;

    @FXML
    private Label lblProgress;

    @FXML
    private ImageView plane;
    private DashboardController dController;

    public NodeFlightController(DashboardController dController) {
        this.dController = dController;
    }

    public DashboardController getDController(){
        return dController;
    }

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
        setFlightProgress(flight);
        isActive(flight);
    }

    public void isActive(Flight flight) {
        long seconds = flight.getDuration();
        long hours = seconds / 3600;
        seconds -= (hours * 3600);
        Long minutes = seconds / 60;
        txtDuration.setText(hours + " hours " + minutes + " minutes");
        FlightThread ft = new FlightThread(flight, this);
        ft.start();
    }

    public void initState(FlightState state) {
        if (state == FlightState.CANCELED || state == FlightState.DELAYED) {
            error.toFront();
            warning.setVisible(false);
            error.setText(state.toString());
        } else if (state == FlightState.DONE || state == FlightState.AIRBORNE) {
            active.toFront();
            warning.setVisible(false);
            active.setText(state.toString());
        } else {
            suspense.toFront();
            warning.setVisible(true);
            suspense.setText(state.toString());
        }
    }

    public void setFlightProgress(Flight flight) {
        plane.setLayoutX(flight.getPosition());
        barProgress.setProgress((flight.getPosition() - 125) / 700.0);
        flight.setProgress((int) Math.round(barProgress.getProgress() * 100.0));
        if (flight.getProgress() > 100) {
            flight.setProgress(100);
        }
        lblProgress.setText(flight.getProgress() + " %");
    }
}
