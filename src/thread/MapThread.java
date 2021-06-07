package thread;

import controller.view.MapController;
import javafx.application.Platform;
import model.Flight;
import model.FlightState;
import route.Route;

public class MapThread extends Thread {
    private Flight flight;
    private MapController mapController;
    private int node;
    private final String ERROR_MESSAGE = "Hey an interrupted exception happened, dont worry";
    private final int LIMIT_JOURNEY = 100;

    public MapThread(Flight flight, MapController mapController,int node) {
        this.flight = flight;
        this.mapController = mapController;
        this.node = node;
    }

    @Override
    public void run() {
        if (flight.getFlightStatus() == FlightState.AIRBORNE) {
            try {
                Thread.sleep(2300);
            } catch (InterruptedException e) {
                mapController.getDController().alert(Route.ERROR, ERROR_MESSAGE);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (flight.getProgress() <= LIMIT_JOURNEY) {
                        mapController.setAircraft(flight,node);
                    } else {
                        flight.setFlightStatus(FlightState.DONE);
                    }
                }
            });
            run();
        }

    }
}
