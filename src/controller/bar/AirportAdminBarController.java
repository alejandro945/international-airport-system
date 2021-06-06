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
    private ImageView imgUsers;

    @FXML
    private ImageView imgAirlines;

    public AirportAdminBarController(DashboardController dController) {
        this.dController = dController;
    }
    @FXML
    private ImageView imgHome;
    
    @FXML
    void showHome(MouseEvent event) throws IOException {
        resetAllIcons();
        imgHome.setImage(new Image("/ui/assets/img/icons/active-dashboard.png"));
        dController.loadView(Route.HOME);
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
        imgHome.setImage(new Image("/ui/assets/img/icons/tickets-flat.png"));
        imgUsers.setImage(new Image("/ui/assets/img/icons/employees.png"));
        imgAirlines.setImage(new Image("/ui/assets/img/icons/plane-flat.png"));
    }

}
