package controller.crud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controller.DashboardController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Airline;
import model.Airport;
import route.Route;

public class LugaggeController {
    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<?> luggageTbl;

    @FXML
    private TableColumn<?, ?> widthCol;

    @FXML
    private TableColumn<?, ?> heightCol;

    @FXML
    private TableColumn<?, ?> WeightCol;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> actionsCol;

    @FXML
    private Button btnEdit;

    @FXML
    private Label modalName;

    @FXML
    private Button btnSave;

    @FXML
    private JFXTextField txtHeight;

    @FXML
    private JFXTextField txtWidth;

    @FXML
    private JFXTextField txtWeight;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXComboBox<?> cbType;

    @FXML
    private JFXTextField txtCharge;
    private Airport airport;
    private DashboardController dController;
    private Airline selected;
    private Stage modal;

    public LugaggeController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @FXML
    void newLuggage(ActionEvent event) throws IOException {
        Stage stage = dController.loadModal(Route.LUGAGGE_MODAL, this);
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
    void editLuggage(ActionEvent event) {

    }

    @FXML
    void saveLuggage(ActionEvent event) {

    }

}
