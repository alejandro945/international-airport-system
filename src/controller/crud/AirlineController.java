package controller.crud;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import controller.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Airline;
import model.Airport;
import route.Route;

public class AirlineController {
    @FXML
    private Button btnEdit;

    @FXML
    private Label modalName;

    @FXML
    private Button btnSave;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtId;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<?> airlineTbl;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> logoCol;

    @FXML
    private TableColumn<?, ?> actionsCol;

    private Airport airport;
    private DashboardController dController;
    private Airline selected;
    private Stage modal;

    public AirlineController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @FXML
    void newAirline(ActionEvent event) throws IOException {
        Stage stage = dController.loadModal(Route.AIRLINE_MODAL, this);
        setModal(stage);
        stage.show();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    void addLogo(ActionEvent event) {

    }

    @FXML
    void cancelModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    void editAirline(ActionEvent event) {

    }

    @FXML
    void saveAirline(ActionEvent event) {

    }
}
