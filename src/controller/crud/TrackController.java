package controller.crud;

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
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import controller.DashboardController;

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
        airport.addTrack(new Track(Integer.parseInt("5912315"), txtGate.getText()));
    }

    public void getData() {
        ObservableList<Track> tracks = FXCollections.observableArrayList(airport.tracksToList());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        renderActions();
        trackTbl.setItems(tracks);
    }

    private void renderActions() {
        Callback<TableColumn<Track, String>, TableCell<Track, String>> cellFact = (TableColumn<Track, String> param) -> {
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
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            selected = (Track) getTableRow().getItem();
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
        Stage stage = dController.loadModal(Route.TRACK_MODAL, this);
        setModal(stage);
        stage.show();
    }

    public void prepareEdition(Track selected) {
        txtId.setText(String.valueOf(selected.getId()));
        txtGate.setText(selected.getGate());
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
    }

}
