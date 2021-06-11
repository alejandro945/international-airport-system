package controller.login;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.AirportController;
import controller.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import model.Airport;
import route.Route;

import java.io.IOException;

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

    public void authGoogle(String[] googleUser) throws Exception {
        airportController.createAlert(
                airport.createUser(googleUser[1], googleUser[2],
                        Long.parseLong(googleUser[0].substring(0, googleUser[0].length() - 4)), googleUser[3]),
                Route.SUCCESS);
        airportController.renderScreen(Route.DASHBOARD);
        airport.saveData();
        airport.loadData();
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
    private void logIn(ActionEvent event) throws Exception {
        validateUser();
    }

    @FXML
    private void createAccount(ActionEvent event) {
        createUser();
    }

    @FXML
    private void keyPress1(KeyEvent event) throws Exception {
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

    public void validateUser() throws IOException, InterruptedException {
        if (!validateLoginFields()) {
            boolean render = airport.userVerification(Long.parseLong(txtLogin.getText()), txtPass.getText());
            if (render) {
                airportController.renderScreen(Route.DASHBOARD);
            } else {
                airportController.createAlert(Constant.USER_NOT_EXIST, Route.ERROR);
            }
        } else {
            airportController.createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    private void createUser() {
        String render = validateRegisterFields();
        if (render.isEmpty()) {
            String msg = airport.createUser(lblName.getText(), lblLastName.getText(), Long.parseLong(lblId.getText()),
                    lblEmail.getText(), lblPass.getText());
            airportController.createAlert(msg, Route.SUCCESS);
            trimFields();
            airport.saveData();
            airport.loadData();
        } else {
            airportController.createAlert(render, Route.WARNING);
        }
    }

    private void trimFields() {
        lblName.setText("");
        lblLastName.setText("");
        lblEmail.setText("");
        lblId.setText("");
        lblPass.setText("");
        lblCofirmPass.setText("");
    }

    private String validateRegisterFields() {
        String msg = "";
        if (lblName.getText().isEmpty() || lblLastName.getText().isEmpty() || lblEmail.getText().isEmpty()
                || lblId.getText().isEmpty() || lblPass.getText().isEmpty()) {
            msg = Constant.EMPTY_FIELDS;
        } else if (!lblPass.getText().equals(lblCofirmPass.getText())) {
            msg = Constant.PASSWORD_CREATION_ERROR;
        } else if (lblPass.getText().length() <= 6) {
            msg = Constant.PASSWORD_WEAK_ERROR;
        } else if (!cbPolicies.isSelected()) {
            msg = Constant.POLICIES_ERROR;
        }
        return msg;
    }

    private boolean validateLoginFields() {
        boolean empty = false;
        if (txtLogin.getText().isEmpty() || txtPass.getText().isEmpty()) {
            empty = true;
        }
        return empty;
    }

}
