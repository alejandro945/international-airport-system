package thread;

import controller.view.IndicatorsController;
import javafx.application.Platform;
import model.Migration;
import route.Route;

public class MigrationThread extends Thread {
    private final int PERSON_DURATION = 2000;
    private final String ERROR_MESSAGE = "Hey an interrupted exception happened, dont worry";
    private IndicatorsController iController;
    private Migration migration;

    public MigrationThread(IndicatorsController iController, Migration migration) {
        this.iController = iController;
        this.migration = migration;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(PERSON_DURATION);
        } catch (InterruptedException e) {
            iController.getDController().geAirportController().createAlert(ERROR_MESSAGE, Route.ERROR);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
