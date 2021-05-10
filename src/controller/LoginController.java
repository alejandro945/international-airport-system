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
        WebEngine webEngine = googleAuth.getEngine();
        com.sun.javafx.webkit.WebConsoleListener.setDefaultListener((webView, message, lineNumber,
                sourceId) -> System.out.println("Console: [" + sourceId + ":" + lineNumber + "] " + message));
        webEngine.load("https://calendar.google.com");
        googleAuth.toFront();
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
