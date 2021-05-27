package controller.view;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewAirlineController implements Initializable {

    @FXML
    private ImageView imgAirlineLogo;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtId;

    private final DashboardController dController;

    public NewAirlineController(DashboardController dController) {
        this.dController = dController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgAirlineLogo.setImage(new Image("/img/icons/image.png"));
    }

    @FXML
    void addAirline(ActionEvent event) {

    }

    @FXML
    void deleteImage(ActionEvent event) {

    }

    @FXML
    void uploadImage(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        dController.loadView(Route.AIRLINES);
    }
}
