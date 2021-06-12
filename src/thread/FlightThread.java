package thread;

import controller.node.NodeFlightController;
import javafx.application.Platform;
import model.Flight;
import model.FlightState;
import route.Route;

import java.io.IOException;

public class FlightThread extends Thread {
    private final String IO_MESSAGE = "Hey an IO exception happened, dont worry";
    private final int LIMIT_JOURNEY = 100;
    private final int SCALE = 1000000;
    private Flight flight;
    private NodeFlightController nf;

    /**
     * The constructor method of a Flight Thread<br>
     */
    public FlightThread(Flight flight, NodeFlightController nf) {
        this.flight = flight;
        this.nf = nf;
    }

    /**
     * 
     * Method needed by the thread class to efficiently start the concurrency in the
     * program<br>
     */
    @Override
    public void run() {
        while (flight.getFlightStatus() == FlightState.AIRBORNE) {
            flight.setPosition((SCALE / flight.getDuration()));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (flight.getProgress() < LIMIT_JOURNEY) {
                        nf.setFlightProgress(flight);
                    } else {
                        if (nf.getDController().getRoute() == Route.ACTIVE_FLIGHTS) {
                            try {
                                nf.getDController().loadView(Route.ACTIVE_FLIGHTS);
                            } catch (IOException e) {
                                nf.getDController().alert(Route.ERROR, IO_MESSAGE);
                            }
                        }
                        flight.setFlightStatus(FlightState.DONE);
                    }
                }
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
