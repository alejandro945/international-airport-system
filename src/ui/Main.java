package ui;

import java.io.IOException;

import controller.AirportController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import model.Airport;
import route.Route;

public class Main extends Application {
    private AirportController airportController;
    private Airport airport;

    public Main() {
        airport = new Airport();
        airportController = new AirportController(airport);
    }

    public static void main(String[] args) {
        launch(args);
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
        airportController.changeScreen(Route.LOGIN);
    }

    @Override
    public void init() throws IOException {

    }
}
