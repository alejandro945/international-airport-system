package controller.view;

import com.jfoenix.controls.JFXTextField;
import controller.Constant;
import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Airport;
import model.User;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private DashboardController dController;
    private Airport airport;

    public ProfileController(DashboardController dController, Airport airport) {
        this.dController = dController;
        this.airport = airport;
    }

    @FXML
    private Label lblUser;

    @FXML
    private ImageView userIcon;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtLast;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPass;

    @FXML
    private JFXTextField txtPassC;
    private User render;

    @FXML
    public void closeAccount(ActionEvent event) throws IOException, InterruptedException {
        dController.closeAccount();
    }

    @FXML
    public void saveData(ActionEvent event) {
        if (!txtPass.getText().equals(txtPassC.getText())) {
            dController.alert(Route.WARNING, Constant.PASSWORD_CREATION_ERROR);
        } else if (!validateFields()) {
            dController.alert(Route.WARNING, Constant.EMPTY_FIELDS);
        } else {
            dController.alert(Route.SUCCESS, airport.editUser(render, txtName.getText(), txtLast.getText(),
                    Long.parseLong(txtId.getText()), txtEmail.getText(), txtPass.getText(), render.getRole()));
            txtPass.clear();
            txtPassC.clear();
        }
    }

    public boolean validateFields() {
        boolean render = true;
        if (txtName.getText().isEmpty() || txtLast.getText().isEmpty() || txtEmail.getText().isEmpty()
                || txtId.getText().isEmpty() || txtPass.getText().isEmpty() || txtPassC.getText().isEmpty()) {
            render = false;
        }
        return render;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (dController.getActiveUser() == 1) {
            lblUser.setText(airport.getLogged().getName() + " " + airport.getLogged().getLastName());
            txtName.setText(airport.getLogged().getName());
            txtLast.setText(airport.getLogged().getLastName());
            txtEmail.setText(airport.getLogged().getEmail());
            txtId.setText(String.valueOf(airport.getLogged().getId()));
            String path = airport.getLogged().getIconPath();
            if (path != null) {
                userIcon.setImage(new Image(path));
            }
        } else if (dController.getActiveUser() == 2) {
            lblUser.setText(airport.getAirlineLogged().getName() + " " + airport.getAirlineLogged().getLastName());
            txtName.setText(airport.getAirlineLogged().getName());
            txtLast.setText(airport.getAirlineLogged().getLastName());
            txtEmail.setText(airport.getAirlineLogged().getEmail());
            txtId.setText(String.valueOf(airport.getAirlineLogged().getId()));
        } else {
            lblUser.setText(airport.getAdminLogged().getName() + " " + airport.getAdminLogged().getLastName());
            txtName.setText(airport.getAdminLogged().getName());
            txtLast.setText(airport.getAdminLogged().getLastName());
            txtEmail.setText(airport.getAdminLogged().getEmail());
            txtId.setText(String.valueOf(airport.getAdminLogged().getId()));
        }
        render = airport.searchUser(Long.parseLong(txtId.getText()));
    }

}
