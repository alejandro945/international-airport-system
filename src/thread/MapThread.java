package thread;

import java.io.IOException;

import controller.view.ActiveFlightsController;
import javafx.application.Platform;
import model.Flight;
import model.FlightState;
import route.Route;

public class MapThread extends Thread {
    private Flight flight;
    private ActiveFlightsController aController;
    private int node;
    private final String INTERRUPTED_MESSAGE = "Hey an interrupted exception happened, dont worry";
    private final String IO_MESSAGE = "Hey an IO exception happened, dont worry";
    private final int LIMIT_JOURNEY = 100;

    public MapThread(Flight flight, ActiveFlightsController aController,int node) {
        this.flight = flight;
        this.aController = aController;
        this.node = node;
    }

    @Override
    public void run() {
        if (flight.getFlightStatus() == FlightState.AIRBORNE) {
            try {
                Thread.sleep(2300);
            } catch (InterruptedException e) {
                aController.getDController().alert(Route.ERROR, INTERRUPTED_MESSAGE);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (flight.getProgress() <= LIMIT_JOURNEY) {
                        aController.setAircraft(flight,node);
                    } else {
                        flight.setFlightStatus(FlightState.DONE);
                        try {
                            aController.getDController().loadView(Route.ACTIVE_FLIGHTS);
                        } catch (IOException e) {
                            aController.getDController().alert(Route.ERROR, IO_MESSAGE);
                        }
                    }
                }
            });
            run();
        }

    }
}
