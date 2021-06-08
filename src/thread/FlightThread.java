package thread;

import java.io.IOException;

import controller.node.NodeFlightController;
import javafx.application.Platform;
import model.Flight;
import model.FlightState;
import route.Route;

public class FlightThread extends Thread {
    private final String IO_MESSAGE = "Hey an IO exception happened, dont worry";
    private final int LIMIT_JOURNEY = 100;
    private final int SCALE = 1000000;
    private Flight flight;
    private NodeFlightController nf;

    public FlightThread(Flight flight, NodeFlightController nf) {
        this.flight = flight;
        this.nf = nf;
    }

    @Override
    public void run() {
        while (flight.getFlightStatus() == FlightState.AIRBORNE) {
            flight.setPosition(( SCALE/ flight.getDuration()));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (flight.getProgress() < LIMIT_JOURNEY) {
                        nf.setFlightProgress(flight);
                    } else {
                        try {
                            nf.getDController().loadView(Route.ACTIVE_FLIGHTS);
                        } catch (IOException e) {
                            nf.getDController().alert(Route.ERROR, IO_MESSAGE);
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
