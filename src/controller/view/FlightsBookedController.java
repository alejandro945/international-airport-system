package controller.view;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import controller.DashboardController;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


import model.*;
import route.Route;

public class FlightsBookedController {
    private Airport airport;
    private DashboardController dController;

    public FlightsBookedController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @FXML
    private JFXComboBox<String> destiny;

    @FXML
    private JFXDatePicker date;

    @FXML
    private Label filterInfo;

    @FXML
    private TableView<Trip> bookingsTable;

    @FXML
    private TableColumn<Trip, String> airlineT;

    @FXML
    private TableColumn<Trip, String> dateT;

    @FXML
    private TableColumn<Trip, String> placeT;

    @FXML
    private TableColumn<Trip, String> idT;

    @FXML
    private TableColumn<Trip, String> seatT;

    @FXML
    private TableColumn<Trip, String> costT;

    @FXML
    private TableColumn<Trip, String> actionT;

    @FXML
    void filter(ActionEvent event) {
        if (date.getValue() == null || destiny.getValue().equals("Countries")) {
            dController.geAirportController().createAlert("Please fill the country and start date field", Route.ERROR);
        } else {
            filterInfo.setText(
                    "Flights to " + destiny.getValue() + " Starting date of search " + date.getValue().toString());
        }
    }

    public void initialize() {
        filterInfo.setText("");
        loadData();
        initializeTableView();
    }

    public void loadData() {
        List<String> types1 = new ArrayList<>();
        int i = 0;

        do {
            if (Places.values()[i].name() != null) {
                types1.add(Places.values()[i].name());
                i++;
            }

        } while (i < Places.values().length);

        ObservableList<String> optionsComboBox1 = FXCollections.observableArrayList(types1);
        destiny.setValue("Countries");
        destiny.setItems(optionsComboBox1);
    }

    public void initializeTableView() {
        // Table Luggage

        ObservableList<Trip> userTrips = FXCollections.observableList(airport.getLogged().getTrips());

        idT.setCellValueFactory(new PropertyValueFactory<Trip, String>("id"));

        airlineT.setCellValueFactory(new Callback<CellDataFeatures<Trip, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Trip, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getTicket().getFlight().getAirline().getAirlineName());
            }
        });
        
        dateT.setCellValueFactory(new Callback<CellDataFeatures<Trip, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Trip, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getTicket().getFlight().getDepartureDate().toString());
            }
        });


        placeT.setCellValueFactory(new Callback<CellDataFeatures<Trip, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Trip, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getTicket().getFlight().getDestination().name());
            }
        });


        /* idT.setCellValueFactory(new Callback<CellDataFeatures<Trip, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Trip, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getId());
            }
        }); */

        seatT.setCellValueFactory(new Callback<CellDataFeatures<Trip, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Trip, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().seatToString());
            }
        });


        costT.setCellValueFactory(new PropertyValueFactory<Trip, String>("tripPrice"));

        
        actionT.setCellValueFactory(new PropertyValueFactory<Trip, String>(""));

        bookingsTable.setItems(userTrips);
    }

}
