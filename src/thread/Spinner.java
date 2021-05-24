package thread;

import java.io.IOException;

import controller.AirportController;
import javafx.application.Platform;
import route.Route;

public class Spinner extends Thread {
    private final int SLEEP_TIME = 4000;
    private AirportController ac;
    private Route redirect;

    public Spinner(Route redirect, AirportController ac) {
        this.redirect = redirect;
        this.ac = ac;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ac.renderScreen(redirect);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

}
