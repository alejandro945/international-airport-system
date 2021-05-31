package controller.crud;

import javafx.event.ActionEvent;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Airline;
import model.Airport;
import route.Route;

public class AircraftController {
    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<?> aircraftTbl;

    @FXML
    private TableColumn<?, ?> codeCol;

    @FXML
    private TableColumn<?, ?> weightCol;

    @FXML
    private TableColumn<?, ?> capacityCol;

    @FXML
    private TableColumn<?, ?> actionsCol;
    @FXML
    private Button btnEdit;

    @FXML
    private Label modalName;

    @FXML
    private Button btnSave;

    @FXML
    private JFXTextField txtWeight;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtCapacity;
    private Airport airport;
    private DashboardController dController;
    private Airline selected;
    private Stage modal;

    public AircraftController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @FXML
    void newAircraft(ActionEvent event) throws IOException {
        Stage stage = dController.loadModal(Route.AIRCRAFT_MODAL, this);
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
    void editAircraft(ActionEvent event) {

    }

    @FXML
    void saveAircraft(ActionEvent event) {

    }

}
