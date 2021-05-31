package ui;

import java.io.IOException;

import com.sun.javafx.application.LauncherImpl;

import controller.AirportController;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import model.Airport;
import route.Route;

public class Main extends Application {
    private final int RENDER_COMPONENTES = 5000;
    private AirportController airportController;
    private Airport airport;

    public Main() {
        airport = new Airport();
        airportController = new AirportController(airport);
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, Splash.class, args);
        //launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(Route.MAINPANE.getRoute()));
        fxmlloader.setController(airportController);
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        airportController.renderScreen(Route.LOGIN);
    }

    @Override
    public void init() throws IOException {
        for (int i = 0; i < RENDER_COMPONENTES; i++) {
            double progress = (100 * i) / RENDER_COMPONENTES;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }
    }
}
