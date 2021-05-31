package controller.bar;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import route.Route;

import java.io.IOException;

public class AirportAdminBarController {

    private final DashboardController dController;

    @FXML
    private ImageView imgTracks;

    @FXML
    private ImageView imgUsers;

    @FXML
    private ImageView imgAirlines;

    public AirportAdminBarController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void showTracks(MouseEvent event) throws IOException {
        resetAllIcons();
        imgTracks.setImage(new Image("/ui/assets/img/icons/active-control-tower.png"));
        dController.loadView(Route.TRACK_TABLE);
    }

    @FXML
    void showUserCrud(MouseEvent event) throws IOException {
        resetAllIcons();
        imgUsers.setImage(new Image("/ui/assets/img/icons/active-employees.png"));
        dController.loadView(Route.USER_TABLE);
    }

    @FXML
    void showAirlines(MouseEvent event) throws IOException {
        resetAllIcons();
        imgAirlines.setImage(new Image("/ui/assets/img/icons/active-plane-flat.png"));
        dController.loadView(Route.AIRLINE_TABLE);
    }

    public void resetAllIcons() {
        imgTracks.setImage(new Image("/ui/assets/img/icons/control-tower.png"));
        imgUsers.setImage(new Image("/ui/assets/img/icons/employees.png"));
        imgAirlines.setImage(new Image("/ui/assets/img/icons/plane-flat.png"));
    }

}
