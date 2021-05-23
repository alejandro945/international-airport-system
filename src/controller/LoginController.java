package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.*;
import model.Airport;

public class LoginController {

    @FXML
    private Pane infoSingup;

    @FXML
    private Pane infoSingin;

    @FXML
    private Pane infoGoogle;

    @FXML
    private Pane signup;

    @FXML
    private Pane singin;

    @FXML
    private Pane googlePane;
    @FXML
    private WebView googleAuth;

    @FXML
    private JFXCheckBox cbPolicies;

    @FXML
    private JFXTextField lblEmail;

    @FXML
    private JFXTextField lblName;

    @FXML
    private JFXTextField lblLastName;

    @FXML
    private JFXPasswordField lblPass;

    @FXML
    private JFXPasswordField lblCofirmPass;

    @FXML
    private JFXTextField lblId;

    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXPasswordField txtPass;

    private AirportController airportController;
    private Airport airport;

    public LoginController(Airport airport, AirportController airportController) {
        this.airportController = airportController;
        this.airport = airport;
    }

    public void authGoogle(String[] googleUser) throws IOException {
        airportController.createAlert(airport.createUser(googleUser[1], googleUser[2],
                Long.parseLong(googleUser[0].substring(0, googleUser[0].length() - 4)), googleUser[3]));
        airportController.showDashboard();
    }

    /**
     * @param event
     */
    @FXML
    public void googleAuth(ActionEvent event) {
        String gClientId = "222822404785-n40avtj1dmpb3dkh1hdj96og5o5relf3.apps.googleusercontent.com";
        String gRedir = "https://international-airport-31fd4.web.app/";
        String gSecret = "_AwJffmnRwGryFre5qg7LZix";
        OAuthAuthenticator auth = new OAuthAuthenticator(gClientId, gRedir, gSecret);
        auth.startLogin(googleAuth, googlePane, infoGoogle, airportController, this);
    }

    /**
     * @param event
     */
    @FXML
    public void singIn(ActionEvent event) {
        new FadeIn(singin).play();
        singin.toFront();
        new FadeIn(infoSingin).play();
        infoSingin.toFront();
    }

    /**
     * @param event
     */
    @FXML
    public void singUp(ActionEvent event) {
        new FadeIn(signup).play();
        signup.toFront();
        new FadeIn(infoSingup).play();
        infoSingup.toFront();
    }

    @FXML
    private void logIn(ActionEvent event) throws IOException {
        validateUser();
    }

    @FXML
    private void createAccount(ActionEvent event) {
        createUser();
    }

    public void validateUser() throws IOException {
        boolean render = airport.userVerification(Long.parseLong(txtLogin.getText()), txtPass.getText());
        if (render) {
            airportController.showDashboard();
        } else {
            airportController.createAlert(Constant.USER_NOT_EXIST);
        }

    }

    private void createUser() {
        String render = validateFields();
        if (render.isEmpty()) {
            String msg = airport.createUser(lblName.getText(), lblLastName.getText(), Long.parseLong(lblId.getText()),
                    lblEmail.getText(), lblPass.getText());
            airportController.createAlert(msg);
            trimFields();
        } else {
            airportController.createAlert(render);
        }
    }

    private void trimFields() {

    }

    private String validateFields() {
        String msg = "";
        if (!lblPass.getText().equals(lblCofirmPass.getText())) {
            msg = Constant.PASSWORD_CREATION_ERROR;
        } else if (lblPass.getText().length() <= 6) {
            msg = Constant.PASSWORD_WEAK_ERROR;
        } else if (lblName.getText().isEmpty() || lblLastName.getText().isEmpty() || lblEmail.getText().isEmpty()
                || lblId.getText().isEmpty() || lblPass.getText().isEmpty()) {
            msg = Constant.EMPTY_FIELDS;
        } else if (!cbPolicies.isSelected()) {
            msg = Constant.POLICIES_ERROR;
        }
        return msg;
    }

    @FXML
    private void keyPress1(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            validateUser();
        }
    }

    @FXML
    private void keyPress2(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            createUser();
        }
    }
}
