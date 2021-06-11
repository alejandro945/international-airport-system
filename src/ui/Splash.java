package ui;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import route.Route;

public class Splash extends Preloader {
    private Stage splash;

    @Override
    public void start(Stage primaryStage) throws Exception {
        splash = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Route.SPINNER.getRoute()));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        splash.setScene(scene);
        splash.initStyle(StageStyle.TRANSPARENT);
        splash.show();
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                splash.hide();
                break;
            default:
                break;
        }
    }
}
