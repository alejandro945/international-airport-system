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
    private ImageView imgSupervisors;

    @FXML
    private ImageView imgMigrationAgents;

    @FXML
    private ImageView imgAirlines;

    public AirportAdminBarController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void showSupervisors(MouseEvent event) throws IOException {
        resetAllIcons();
        imgSupervisors.setImage(new Image("/ui/assets/img/icons/active-control-tower.png"));
        dController.loadView(Route.AP_EMPLOYEES);
    }

    @FXML
    void showUserCrud(MouseEvent event) throws IOException {
        resetAllIcons();
        imgSupervisors.setImage(new Image("/ui/assets/img/icons/active-control-tower.png"));
        dController.loadView(Route.USER_TABLE);
    }

    @FXML
    void showMigrationAgents(MouseEvent event) throws IOException {
        resetAllIcons();
        imgMigrationAgents.setImage(new Image("/ui/assets/img/icons/active-employees.png"));
        dController.loadView(Route.AP_EMPLOYEES);
    }

    @FXML
    void showAirlines(MouseEvent event) throws IOException {
        resetAllIcons();
        imgAirlines.setImage(new Image("/ui/assets/img/icons/active-plane-flat.png"));
        dController.loadView(Route.AIRLINES);
    }

    public void resetAllIcons() {
        imgSupervisors.setImage(new Image("/ui/assets/img/icons/control-tower.png"));
        imgMigrationAgents.setImage(new Image("/ui/assets/img/icons/employees.png"));
        imgAirlines.setImage(new Image("/ui/assets/img/icons/plane-flat.png"));
    }

}
