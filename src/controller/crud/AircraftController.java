package controller.crud;

import controller.Constant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Airline;
import model.Airport;
import model.Aircraft;
import route.Route;

public class AircraftController implements Initializable {
    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<Aircraft> aircraftTbl;

    @FXML
    private TableColumn<?, ?> codeCol;

    @FXML
    private TableColumn<?, ?> weightCol;

    @FXML
    private TableColumn<?, ?> capacityCol;

    @FXML
    private TableColumn<Aircraft, String> actionsCol;
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
    private Aircraft selected;
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
        if (validateFields()) {
            selected.setPlaneCode(txtCode.getText());
            selected.setPlaneWeight(Integer.parseInt(txtWeight.getText()));
            selected.setCapacity(Integer.parseInt(txtCapacity.getText()));
            dController.geAirportController().createAlert("Aircraft was successfully edited.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @FXML
    void saveAircraft(ActionEvent event) {
        if (validateFields()) {
            dController.geAirportController().createAlert("Aircraft was successfully added.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }

    private void getData() {
        //ObservableList<Aircraft> aircrafts = FXCollections.observableArrayList(airline.getAircraft());
        codeCol.setCellValueFactory(new PropertyValueFactory<>("planeCode"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("planeWeight"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        renderActions();
        //aircraftTbl.setItems(aircrafts);
    }

    public boolean validateFields() {
        boolean render = true;
        if (txtCapacity.getText().isEmpty() || txtWeight.getText().isEmpty() || txtCode.getText().isEmpty()) {
            render = false;
        }
        return render;
    }

    private void renderActions() {
        Callback<TableColumn<Aircraft, String>, TableCell<Aircraft, String>> cellFact = (
                TableColumn<Aircraft, String> param) -> {
            final TableCell<Aircraft, String> cell = new TableCell<Aircraft, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button delete = new Button("Delete");
                        delete.setId("delete");
                        Button edit = new Button("Edit");
                        edit.setId("edit");
                        delete.getStylesheets().add(Route.CRUD.getRoute());
                        edit.getStylesheets().add(Route.CRUD.getRoute());
                        delete.setOnAction((ActionEvent event) -> {
                            selected = (Aircraft) getTableRow().getItem();
                            selected.getAirline().getAircraft().remove(selected);
                            dController.geAirportController().createAlert("Aircraft was removed successfully.", Route.SUCCESS);
                            getData();
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            selected = (Aircraft) getTableRow().getItem();
                            try {
                                showModal();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            modalName.setText("Edit User");
                            prepareEdition(selected);
                        });
                        HBox managebtn = new HBox(edit, delete);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(delete, new Insets(2, 2, 0, 3));
                        HBox.setMargin(edit, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionsCol.setCellFactory(cellFact);
    }

    public void showModal() throws IOException {
        Stage stage = dController.loadModal(Route.AIRCRAFT_MODAL, this);
        setModal(stage);
        stage.show();
    }

    public void prepareEdition(Aircraft selected) {
        txtCode.setText(selected.getPlaneCode());
        txtWeight.setText(String.valueOf(selected.getPlaneWeight()));
        txtCapacity.setText(String.valueOf(selected.getCapacity()));
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
    }
}
