package controller.crud;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

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
import javafx.util.converter.LocalDateStringConverter;
import model.*;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private TableColumn<?, ?> pilotCol;

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
    private JFXDatePicker txtTakesOffDate;

    @FXML
    private JFXTimePicker txtTakesOffTime;

    @FXML
    private JFXComboBox<Pilot> cbPilot;

    @FXML
    private JFXDatePicker txtArrivalDate;

    @FXML
    private JFXTimePicker txtArrivalTime;

    @FXML
    private JFXComboBox<FlightState> cbStatus;

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
        dateFormat();
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

    private void dateFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        txtTakesOffDate.setConverter(new LocalDateStringConverter(formatter, null));
        txtArrivalDate.setConverter(new LocalDateStringConverter(formatter, null));
    }

    public void initComboBoxes() {
        cbAircraft.getItems().addAll(airline.getAircraft());
        cbPilot.getItems().addAll(airline.getPilots());
        cbTrack.getItems().addAll(airport.tracksToList());
        cbDeparture.getItems().addAll(Places.values());
        cbDestination.getItems().addAll(Places.values());
        cbStatus.getItems().addAll(FlightState.values());
    }

    @FXML
    public void editFlight(ActionEvent event) {
        if (validateFields()) {
            selected.setId(txtID.getText());
            selected.setDepartureDate(txtTakesOffDate.getValue().toString());
            selected.setDepartureHour(txtTakesOffTime.getValue().toString());
            selected.setArrivalDate(txtArrivalDate.getValue().toString());
            selected.setArrivalHour(txtArrivalTime.getValue().toString());
            selected.setOrigin(cbDeparture.getValue());
            selected.setDestination(cbDestination.getValue());
            selected.setTrack(cbTrack.getValue());
            selected.setPlane(cbAircraft.getValue());
            selected.getPlane().setPilot(cbPilot.getValue());
            selected.setFlightStatus(cbStatus.getValue());
            dController.geAirportController().createAlert("Flight was successfully edited.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
        dateFormat();
    }

    @FXML
    void saveFlight(ActionEvent event) {
        if (validateFields()) {
            String id = txtID.getText();
            String departureDate = txtTakesOffDate.getValue().toString();
            String departureHour = txtTakesOffTime.getValue().toString();
            String arrivalDate = txtArrivalDate.getValue().toString();
            String arrivalHour = txtArrivalTime.getValue().toString();
            Places departure = cbDeparture.getValue();
            Places destination = cbDestination.getValue();
            Track track = cbTrack.getValue();
            Aircraft aircraft = cbAircraft.getValue();
            aircraft.setPilot(cbPilot.getValue());
            airline.getFlights().add(new Flight(id, departureDate, departureHour, arrivalDate, arrivalHour, departure,
                    destination, track, airline, aircraft));
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
        pilotCol.setCellValueFactory(new PropertyValueFactory<>("pilot"));
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
        if (txtID.getText().isEmpty() || cbAircraft.getSelectionModel().getSelectedItem() == null
                || cbTrack.getSelectionModel().getSelectedItem() == null
                || cbDeparture.getSelectionModel().getSelectedItem() == null
                || cbDestination.getSelectionModel().getSelectedItem() == null || txtTakesOffDate.getValue() == null
                || txtTakesOffTime.getValue() == null || txtArrivalDate.getValue() == null
                || txtArrivalTime.getValue() == null || cbStatus.getSelectionModel().getSelectedItem() == null) {
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
                            dController.geAirportController().createAlert("Flight was removed successfully.",
                                    Route.SUCCESS);
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
        dateFormat();
        txtID.setText(selected.getId());
        cbAircraft.setValue(selected.getPlane());
        cbPilot.setValue(selected.getPilot());
        cbTrack.setValue(selected.getTrack());
        cbDeparture.setValue(selected.getOrigin());
        cbDestination.setValue(selected.getDestination());
        txtTakesOffDate.setValue(LocalDate.parse(selected.getDepartureDate()));
        txtTakesOffTime.setValue(LocalTime.parse(selected.getDepartureHour()));
        txtArrivalDate.setValue(LocalDate.parse(selected.getArrivalDate()));
        txtArrivalTime.setValue(LocalTime.parse(selected.getArrivalHour()));
        cbStatus.setValue(selected.getFlightStatus());
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
    }
}
