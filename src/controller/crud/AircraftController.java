package controller.crud;

import com.jfoenix.controls.JFXTextField;
import controller.Constant;
import controller.DashboardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Aircraft;
import model.Airline;
import model.Airport;
import route.Route;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Airline airline;
    private Aircraft selected;
    private Stage modal;

    public AircraftController(Airport airport, DashboardController dController, Airline airline) {
        this.airport = airport;
        this.dController = dController;
        this.airline = airline;
    }

    @FXML
    void newAircraft(ActionEvent event) throws IOException {
        if (modal == null) {
            Stage stage = dController.loadModal(Route.AIRCRAFT_MODAL, this);
            setModal(stage);
            stage.show();
        }
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    void cancelModal(ActionEvent event) {
        modal.close();
        setModal(null);
    }

    @FXML
    public void exportInfo(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showSaveDialog(null);
        if (selectedFile != null) {
            dController.alert(Route.SUCCESS, Constant.EXPORT_SUCCESS);
            try {
                airline.exportDataAircraft(selectedFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                dController.alert(Route.ERROR, Constant.FILE_NOT_FOUND);
            }
        }
    }

    @FXML
    public void importInfo(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Resource File");
        File selectedFile = fc.showOpenDialog(null);
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        if (selectedFile != null) {
            dController.alert(Route.SUCCESS, Constant.IMPORT_SUCCESS);
            try {
                airline.importDataAircraft(selectedFile.getAbsolutePath());
            } catch (IOException e) {
                dController.alert(Route.WARNING, Constant.IOEXCEPTION);
            }
            airport.saveData();
            getData();
        }
    }

    @FXML
    public void editAircraft(ActionEvent event) {
        if (validateFields()) {
            selected.setPlaneCode(txtCode.getText());
            selected.setPlaneWeight(Integer.parseInt(txtWeight.getText()));
            selected.setCapacity(Integer.parseInt(txtCapacity.getText()));
            dController.geAirportController().createAlert("Aircraft was successfully edited.", Route.SUCCESS);
            airport.saveData();
            getData();
            modal.close();
            setModal(null);
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @FXML
    public void saveAircraft(ActionEvent event) {
        if (validateFields()) {
            if (airline.searchAircraft(txtCode.getText()) == null) {
                airline.getAircraft().add(new Aircraft(txtCode.getText(), Integer.parseInt(txtWeight.getText()),
                        Integer.parseInt(txtCapacity.getText()), airline));
                dController.alert(Route.SUCCESS, "Aircraft was successfully added.");
            } else {
                dController.alert(Route.ERROR, "Aircraft already exists.");
            }
            airport.saveData();
            getData();
            modal.close();
            setModal(null);
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }

    private void getData() {
        ObservableList<Aircraft> aircrafts = FXCollections.observableArrayList(airline.getAircraft());
        codeCol.setCellValueFactory(new PropertyValueFactory<>("planeCode"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("planeWeight"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        renderActions();
        aircraftTbl.setItems(aircrafts);
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
                            dController.geAirportController().createAlert("Aircraft was removed successfully.",
                                    Route.SUCCESS);
                            getData();
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            if (modal == null) {
                                selected = (Aircraft) getTableRow().getItem();
                                try {
                                    showModal();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                modalName.setText("Edit User");
                                prepareEdition(selected);
                            }
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
