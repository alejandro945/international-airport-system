package controller.crud;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FlightController implements Initializable {

    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<Flight> flightsTbl;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> aircraftCol;

    @FXML
    private TableColumn<?, ?> trackCol;

    @FXML
    private TableColumn<?, ?> departureCol;

    @FXML
    private TableColumn<?, ?> destinationCol;

    @FXML
    private TableColumn<?, ?> takesOffCol;

    @FXML
    private TableColumn<?, ?> arrivesCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableColumn<Flight, String> actionsCol;

    @FXML
    private Button btnEdit;

    @FXML
    private Label modalName;

    @FXML
    private Button btnSave;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXComboBox<Aircraft> cbAircraft;

    @FXML
    private JFXComboBox<Track> cbTrack;

    @FXML
    private JFXComboBox<Places> cbDeparture;

    @FXML
    private JFXComboBox<Places> cbDestination;

    @FXML
    private JFXTextField txtTakesOffDate;

    @FXML
    private JFXTextField txtTakesOffTime;

    @FXML
    private JFXComboBox<FlightState> cbStatus;

    @FXML
    private JFXTextField txtArrivalDate;

    @FXML
    private JFXTextField txtArrivalTime;

    private Airport airport;
    private DashboardController dController;
    private Airline airline;
    private Flight selected;
    private Stage modal;

    public FlightController(Airport airport, DashboardController dController, Airline airline) {
        this.airport = airport;
        this.dController = dController;
        this.airline = airline;
    }

    @FXML
    public void newFlight(ActionEvent event) throws IOException {
        showModal();
        modalName.setText("Create Flight");
        btnEdit.setVisible(false);
        btnSave.setVisible(true);
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    public void cancelModal(ActionEvent event) {
        modal.close();
    }

    public void initComboBoxes() {
        cbAircraft.getItems().addAll(airline.getAircraft());
        cbTrack.getItems().addAll(airport.tracksToList());
        cbDeparture.getItems().addAll(Places.values());
        cbDestination.getItems().addAll(Places.values());
        cbStatus.getItems().addAll(FlightState.values());
    }

    @FXML
    void editFlight(ActionEvent event) {
        if (validateFields()) {
            selected.setId(txtID.getText());
            selected.setDepartureDate(txtTakesOffDate.getText());
            selected.setDepartureHour(txtTakesOffTime.getText());
            selected.setArrivalDate(txtArrivalDate.getText());
            selected.setArrivalHour(txtArrivalTime.getText());
            selected.setOrigin(cbDeparture.getValue());
            selected.setDestination(cbDestination.getValue());
            selected.setTrack(cbTrack.getValue());
            selected.setPlane(cbAircraft.getValue());
            selected.setFlightStatus(cbStatus.getValue());
            dController.geAirportController().createAlert("Flight was successfully edited.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @FXML
    void saveFlight(ActionEvent event) {
        if (validateFields()) {
            String id = txtID.getText();
            String departureDate = txtTakesOffDate.getText();
            String departureHour = txtTakesOffTime.getText();
            String arrivalDate = txtArrivalDate.getText();
            String arrivalHour = txtArrivalTime.getText();
            Places departure = cbDeparture.getValue();
            Places destination = cbDestination.getValue();
            Track track = cbTrack.getValue();
            Aircraft aircraft = cbAircraft.getValue();
            airline.getFlights().add(new Flight(id, departureDate, departureHour, arrivalDate, arrivalHour, departure, destination, track, airline, aircraft));
            dController.geAirportController().createAlert("Flight was successfully added.", Route.SUCCESS);
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
        ObservableList<Flight> flights = FXCollections.observableArrayList(airline.getFlights());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        aircraftCol.setCellValueFactory(new PropertyValueFactory<>("plane"));
        trackCol.setCellValueFactory(new PropertyValueFactory<>("track"));
        departureCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        takesOffCol.setCellValueFactory(new PropertyValueFactory<>("takesOff"));
        arrivesCol.setCellValueFactory(new PropertyValueFactory<>("arrives"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("flightStatus"));
        renderActions();
        flightsTbl.setItems(flights);
    }

    public boolean validateFields() {
        boolean render = true;
        if (txtID.getText().isEmpty() || cbAircraft.getSelectionModel().getSelectedItem() == null || cbTrack.getSelectionModel().getSelectedItem() == null || cbDeparture.getSelectionModel().getSelectedItem() == null || cbDestination.getSelectionModel().getSelectedItem() == null || txtTakesOffDate.getText().isEmpty() || txtTakesOffTime.getText().isEmpty() || txtArrivalDate.getText().isEmpty() || txtArrivalTime.getText().isEmpty() || cbStatus.getSelectionModel().getSelectedItem() == null) {
            render = false;
        }
        return render;
    }

    private void renderActions() {
        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFact = (
                TableColumn<Flight, String> param) -> {
            final TableCell<Flight, String> cell = new TableCell<Flight, String>() {
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
                            selected = (Flight) getTableRow().getItem();
                            selected.getAirline().getFlights().remove(selected);
                            dController.geAirportController().createAlert("Flight was removed successfully.", Route.SUCCESS);
                            getData();
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            selected = (Flight) getTableRow().getItem();
                            try {
                                showModal();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            modalName.setText("Edit Flight");
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
        Stage stage = dController.loadModal(Route.FLIGHTS_MODAL, this);
        setModal(stage);
        stage.show();
        initComboBoxes();
    }

    public void prepareEdition(Flight selected) {
        txtID.setText(selected.getId());
        cbAircraft.setValue(selected.getPlane());
        cbTrack.setValue(selected.getTrack());
        cbDeparture.setValue(selected.getOrigin());
        cbDestination.setValue(selected.getDestination());
        txtTakesOffDate.setText(selected.getDepartureDate());
        txtTakesOffTime.setText(selected.getDepartureHour());
        txtArrivalDate.setText(selected.getArrivalDate());
        txtArrivalTime.setText(selected.getArrivalHour());
        cbStatus.setValue(selected.getFlightStatus());
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
    }
}
