package controller.bar;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import route.Route;

import java.io.IOException;

public class MigrationAgentBarController {
    @FXML
    private ImageView imgIndicators;
    private final DashboardController dController;

    public MigrationAgentBarController(DashboardController dController) {
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
    void showIndicators(MouseEvent event) throws IOException {
        resetAllIcons();
        imgIndicators.setImage(new Image("/ui/assets/img/icons/active-dashboard.png"));
        dController.loadView(Route.INDICATORS);
    }

    public void resetAllIcons() {
        imgHome.setImage(new Image("/ui/assets/img/icons/tickets-flat.png"));
        imgIndicators.setImage(new Image("/ui/assets/img/icons/dashboard.png"));
    }

}
