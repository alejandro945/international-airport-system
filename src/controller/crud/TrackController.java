package controller.crud;

import com.jfoenix.controls.JFXCheckBox;
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
import model.Airport;
import model.Track;
import route.Route;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrackController implements Initializable {
    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<Track> trackTbl;

    @FXML
    private TableColumn<Track, String> idCol;

    @FXML
    private TableColumn<Track, String> gateCol;

    @FXML
    private TableColumn<Track, String> stateCol;

    @FXML
    private TableColumn<Track, String> actionsCol;

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
    @FXML
    private JFXCheckBox cbMaintenance;

    private Airport airport;
    private DashboardController dController;
    private Track selected;
    private Stage modal;

    public TrackController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }

    @FXML
    public void newTrack(ActionEvent event) throws IOException {
        if (modal == null) {
            showModal();
            modalName.setText("Create Track");
            txtId.setText(airport.getTrackAmount() + 1 + "");
            cbMaintenance.setDisable(true);
            btnEdit.setVisible(false);
            btnSave.setVisible(true);
        }
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    public void cancelModal(ActionEvent event) {
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
                airport.exportDataTracks(selectedFile.getAbsolutePath());
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
                airport.importDataTracks(selectedFile.getAbsolutePath());
            } catch (IOException e) {
                dController.alert(Route.WARNING, Constant.IOEXCEPTION);
            }
            airport.saveData();
            getData();
        }
    }

    @FXML
    public void editTrack(ActionEvent event) {
        if (validateFields()) {
            dController.geAirportController().createAlert(
                    airport.editTrack(selected, txtGate.getText(), cbMaintenance.isSelected()), Route.SUCCESS);
            modal.close();
            airport.saveData();
            getData();
            setModal(null);
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @FXML
    public void saveTrack(ActionEvent event) {
        if (validateFields()) {
            airport.addTrack(new Track(Integer.parseInt(txtId.getText()), txtGate.getText()));
            dController.geAirportController().createAlert("Track was successfully added.", Route.SUCCESS);
            airport.saveData();
            getData();
            modal.close();
            setModal(null);
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    public void getData() {
        ObservableList<Track> tracks = FXCollections.observableArrayList(airport.tracksToList());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        renderActions();
        trackTbl.setItems(tracks);
    }

    public boolean validateFields() {
        boolean render = true;
        if (txtGate.getText().isEmpty()) {
            render = false;
        }
        return render;
    }

    private void renderActions() {
        Callback<TableColumn<Track, String>, TableCell<Track, String>> cellFact = (
                TableColumn<Track, String> param) -> {
            final TableCell<Track, String> cell = new TableCell<Track, String>() {
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
                            selected = (Track) getTableRow().getItem();
                            dController.geAirportController().createAlert(airport.removeTrack(selected), Route.SUCCESS);
                            getData();
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            if (modal == null) {
                                selected = (Track) getTableRow().getItem();
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
        Stage stage = dController.loadModal(Route.TRACK_MODAL, this);
        setModal(stage);
        stage.show();
    }

    public void prepareEdition(Track selected) {
        txtId.setText(String.valueOf(selected.getId()));
        txtGate.setText(selected.getGate());
        cbMaintenance.setSelected(selected.isInMaintenance());
        cbMaintenance.setDisable(false);
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
    }

}
