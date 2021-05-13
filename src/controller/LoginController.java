package controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.web.*;
import model.Airport;

public class LoginController {

    @FXML
    private Pane infoSingup;

    @FXML
    private Pane infoSingin;

    @FXML
    private Pane signup;

    @FXML
    private Pane singin;

    @FXML
    private WebView googleAuth;

    public LoginController(Airport airport) {

    }

    @FXML
    void googleAuth(ActionEvent event) {
        String gClientId = "222822404785-n40avtj1dmpb3dkh1hdj96og5o5relf3.apps.googleusercontent.com";
        String gRedir = "https://international-airport-31fd4.web.app/";
        String gSecret = "_AwJffmnRwGryFre5qg7LZix";
        OAuthAuthenticator auth = new OAuthAuthenticator(gClientId, gRedir, gSecret);
        auth.startLogin();
    }

    @FXML
    void singIn(ActionEvent event) {
        new FadeIn(singin).play();
        singin.toFront();
        new FadeIn(infoSingin).play();
        infoSingin.toFront();
    }

    @FXML
    void singUp(ActionEvent event) {
        new FadeIn(signup).play();
        signup.toFront();
        new FadeIn(infoSingup).play();
        infoSingup.toFront();
    }
}
