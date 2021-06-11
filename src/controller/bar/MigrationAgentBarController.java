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

public class MigrationAgentBarController implements Initializable {
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
        imgHome.setImage(new Image("/ui/assets/img/icons/dashboard.png"));
        imgIndicators.setImage(new Image("/ui/assets/img/icons/dashboard.png"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetAllIcons();
        imgHome.setImage(new Image("/ui/assets/img/icons/active-dashboard.png"));
    }

}
