package controller.bar;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import route.Route;

import java.io.IOException;

public class ClientBarController {

    private final DashboardController dController;

    public ClientBarController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    private ImageView imgMyFlights;

    @FXML
    private ImageView imgNewFlight;

    @FXML
    void showMyFlights(MouseEvent event) throws IOException {
        resetAllIcons();
        imgMyFlights.setImage(new Image("/ui/assets/img/icons/active-tickets-flat.png"));
        dController.loadView(Route.MY_TRIPS);
    }

    @FXML
    void showNewFlight(MouseEvent event) throws IOException {
        resetAllIcons();
        imgNewFlight.setImage(new Image("/ui/assets/img/icons/active-plane-flat.png"));
        dController.loadView(Route.NEW_TRIP);
    }

    public void resetAllIcons() {
        imgMyFlights.setImage(new Image("/ui/assets/img/icons/tickets-flat.png"));
        imgNewFlight.setImage(new Image("/ui/assets/img/icons/plane-flat.png"));
    }

}
