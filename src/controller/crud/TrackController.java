package controller.crud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Airport;
import model.Track;
import route.Route;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import controller.DashboardController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TrackController {
    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<?> trackTbl;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> gateCol;

    @FXML
    private TableColumn<?, ?> stateCol;

    @FXML
    private TableColumn<?, ?> actionsCol;
    @FXML
    private Button btnEdit;

    @FXML
    private Label modalName;

    @FXML
    private Button btnSave;

    @FXML
    private JFXTextField txtGate;

    @FXML
    private JFXTextField txtId;

    private Airport airport;
    private DashboardController dController;
    private Track selected;
    private Stage modal;

    public TrackController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @FXML
    void newTrack(ActionEvent event) throws IOException {
        Stage stage = dController.loadModal(Route.TRACK_MODAL, this);
        setModal(stage);
        stage.show();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    void cancelModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    void editTrack(ActionEvent event) {

    }

    @FXML
    void saveTrack(ActionEvent event) {

    }

}
