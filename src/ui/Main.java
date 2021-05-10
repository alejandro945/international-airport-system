package ui;

import controller.AirportController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import model.Airport;

public class Main extends Application {
    private AirportController airportController;
    private Airport airport;

    public Main() {
        airportController = new AirportController(airport);
    }

    public static void main(String[] args) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("screens/mainPane.fxml"));
        fxmlloader.setController(airportController);
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("assets/styles/vendor.css").toExternalForm());
        primaryStage.show();
        airportController.showHome();
    }
}
