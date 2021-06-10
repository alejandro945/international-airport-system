package controller.bar;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientBarController implements Initializable {

    private final DashboardController dController;

    public ClientBarController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    private ImageView imgMyFlights;

    @FXML
    private ImageView imgNewFlight;

    @FXML
    private ImageView imgHome;

    @FXML
    void showHome(MouseEvent event) throws IOException {
        resetAllIcons();
        imgHome.setImage(new Image("/ui/assets/img/icons/active-dashboard.png"));
        dController.loadView(Route.HOME);
    }

    @FXML
    void showMyTrips(MouseEvent event) throws IOException {
        resetAllIcons();
        imgMyFlights.setImage(new Image("/ui/assets/img/icons/active-tickets-flat.png"));
        dController.loadView(Route.MY_TRIPS);
    }

    @FXML
    void showBook(MouseEvent event) throws IOException {
        resetAllIcons();
        imgNewFlight.setImage(new Image("/ui/assets/img/icons/active-plane-flat.png"));
        dController.loadView(Route.NEW_TRIP);
    }

    public void resetAllIcons() {
        imgHome.setImage(new Image("/ui/assets/img/icons/dashboard.png"));
        imgMyFlights.setImage(new Image("/ui/assets/img/icons/tickets-flat.png"));
        imgNewFlight.setImage(new Image("/ui/assets/img/icons/plane-flat.png"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetAllIcons();
        imgHome.setImage(new Image("/ui/assets/img/icons/active-dashboard.png"));
    }

}
