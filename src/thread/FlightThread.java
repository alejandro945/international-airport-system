package thread;

import controller.node.NodeFlightController;
import javafx.application.Platform;
import model.Flight;
import model.FlightState;

public class FlightThread extends Thread {
    private final int SIZE_BAR = 825;
    private Flight flight;
    private NodeFlightController nf;

    public FlightThread(Flight flight, NodeFlightController nf) {
        this.flight = flight;
        this.nf = nf;
    }

    @Override
    public void run() {
        while (flight.getFlightStatus() == FlightState.AIRBORNE) {
            flight.setPosition((1000000 / flight.getDuration()));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (flight.getPosition() <= SIZE_BAR) {
                        nf.setFlightProgress(flight);
                    } else {
                        flight.setFlightStatus(FlightState.DONE);
                        nf.initState(flight.getFlightStatus());
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
