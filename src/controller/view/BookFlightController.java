package controller.view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import controller.DashboardController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import route.Route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookFlightController {

    private Airport airport;
    private DashboardController dController;

    private Stage modal;
    private Stage modalTicket;
    private Stage modalUpdate;

    private Ticket ticket;
    private Trip trip;
    private Flight selectedFlight;
    private Seat selectSeat;
    private Seat previousSeat;
    private int tripPrice;
    private List<Luggage> luggages;
    private boolean isUpdateMode;

    public BookFlightController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
        luggages = new ArrayList<>();
    }

    public void initialize() {
        filterInfo.setText("");
        initializeTableView();
    }

    @FXML
    private Label idTrip;

    @FXML
    private Label filterInfo;

    @FXML
    private JFXComboBox<String> destiny;

    @FXML
    private JFXDatePicker date;

    @FXML
    private VBox luggagePane;

    // Table luggages

    @FXML
    private TableView<Luggage> luggageTbl;

    @FXML
    private TableColumn<Luggage, String> widthCol;

    @FXML
    private TableColumn<Luggage, String> heightCol;

    @FXML
    private TableColumn<Luggage, String> WeightCol;

    @FXML
    private TableColumn<Luggage, String> priceCol;

    @FXML
    private TableColumn<Luggage, String> actionsCol;

    // Table flights

    @FXML
    private TableView<Flight> flightsT;

    @FXML
    private TableColumn<Flight, String> airlineT;

    @FXML
    private TableColumn<Flight, String> dateT;

    @FXML
    private TableColumn<Flight, String> arriveT;

    @FXML
    public void filter(ActionEvent event) {
        if (date.getValue() == null || destiny.getValue().equals("Countries")) {
            dController.geAirportController().createAlert("Please fill the country and start date field", Route.ERROR);
        } else {
            filterInfo.setText(
                    "Flights to " + destiny.getValue() + " Starting date of search " + date.getValue().toString());
        }
    }

    public void newTrip() throws IOException {
        Stage stageTicket = dController.loadModal(Route.TRIP_MODAL, this); /// Doble click vuelo
        this.modalTicket = stageTicket;
        stageTicket.show();
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

        // Table flights

        ObservableList<Flight> flightsAirport = FXCollections.observableList(airport.getFlights());

        airlineT.setCellValueFactory(new PropertyValueFactory<Flight, String>("airline"));
        dateT.setCellValueFactory(new PropertyValueFactory<Flight, String>("departureDate"));
        airlineT.setCellValueFactory(new Callback<CellDataFeatures<Flight, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Flight, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getAirline().getAirlineName());
            }
        });

        arriveT.setCellValueFactory(new Callback<CellDataFeatures<Flight, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Flight, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getDestination().toString());
            }
        });

        flightsT.setItems(flightsAirport);

        flightsT.setRowFactory(tv -> {
            TableRow<Flight> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Flight temp = row.getItem();
                    selectedFlight = temp;
                    try {
                        newTrip();
                        Place.setText("To: " + temp.getDestination().toString());
                        Id.setText("ID: " + temp.getId());
                        tickedType.setDisable(true);
                        Place.setDisable(true);
                        Id.setDisable(true);
                        modalTicketDataLoad();
                        planeSize(temp);
                        seatsGrid.setHgap(5);
                        seatsGrid.setVgap(5);
                        seatsGrid.setPadding(new Insets(5, 5, 5, 5));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        // Table Luggage

        ObservableList<Luggage> luggagesTrip = FXCollections.observableList(this.luggages);

        widthCol.setCellValueFactory(new PropertyValueFactory<Luggage, String>("width"));
        heightCol.setCellValueFactory(new PropertyValueFactory<Luggage, String>("height"));
        WeightCol.setCellValueFactory(new PropertyValueFactory<Luggage, String>("weight"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Luggage, String>("luggagePrice"));

        luggageTbl.setItems(luggagesTrip);

        luggageTbl.setRowFactory(tv -> {
            TableRow<Luggage> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Luggage temp = row.getItem();
                    luggages.remove(temp);
                    initializeTableView();
                }
            });
            return row;
        });
    }

    // Ticket creation

    @FXML
    private JFXTextField Place;

    @FXML
    private JFXTextField Id;

    @FXML
    private JFXTextField seatValue;

    @FXML
    private JFXTextField ticketPrice;

    @FXML
    private JFXComboBox<String> tickedType;

    @FXML
    private JFXComboBox<String> meal;

    @FXML
    private GridPane seatsGrid;

    @FXML
    void cancelTicket(ActionEvent event) {
        modalTicket.close();
    }

    @FXML
    void saveTicket(ActionEvent event) {
        if (validationTicket()) {
            ticket = new Ticket(selectedFlight, selectSeat, Integer.parseInt(ticketPrice.getText()));
            txtFlightId.setText("Flight Code: " + selectedFlight.getId());
            txtTo.setText("Destination: " + selectedFlight.getDestination().name());
            txtSelctedSeat.setText(
                    "Select Seat : " + String.valueOf(selectSeat.getSeatLetter()) + selectSeat.getSeatNumber() + "");
            updateLuggageToTrip(trip);
            calculateTripPrice();
            txtTotalPrice.setText("Total:         $" + tripPrice);
            idTrip.setText(
                    "Trip Id: " + "T" + airport.getLogged().getTrips().size() + "-" + ticket.getFlight().getId());

            modalTicket.close();
            dController.alert(Route.SUCCESS, "Ticket saved");
        } else {
            dController.alert(Route.ERROR, "Please selected a seat by clicking it");
        }
    }

    private boolean validationTicket() {
        if (ticketPrice.getText().equals("") || seatValue.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public void modalTicketDataLoad() {
        List<String> types1 = new ArrayList<>();
        int i = 0;

        do {
            if (Meal.values()[i].name() != null) {
                types1.add(Meal.values()[i].name());
                i++;
            }

        } while (i < Meal.values().length);

        ObservableList<String> optionsComboBox1 = FXCollections.observableArrayList(types1);

        meal.setItems(optionsComboBox1);

        // ---------------------

        List<String> types2 = new ArrayList<>();

        types2.add("Standard");
        types2.add("Premium");

        ObservableList<String> optionsComboBox2 = FXCollections.observableArrayList(types2);

        tickedType.setItems(optionsComboBox2);
    }

    public void planeSize(Flight flight) {
        seatsGrid.getChildren().clear();
        int y = flight.getPlane().getCapacity() / 6;

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < 6; j++) {

                Seat tempSeat = flight.getPlane().getSeat()[i][j];

                String letter = String.valueOf(tempSeat.getSeatLetter());
                int number = tempSeat.getSeatNumber();
                Label temp = new Label(letter + " " + number);

                GridPane tempGrid = new GridPane();

                tempGrid.setAlignment(Pos.CENTER);
                tempGrid.add(temp, 0, 0);
                if (tempSeat.getSeatState()) {
                    tempGrid.setStyle(
                            "-fx-background-color: rgb(179, 176, 176); -fx-border-color: gray; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                } else {
                    tempGrid.setStyle("-fx-background-color: none; -fx-border-color: gray; -fx-border-radius: 5px;");
                }
                tempGrid.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        onCLickActionSeat(e, flight);
                    }
                });
                seatsGrid.add(tempGrid, j, i);
            }
        }
        boxStyle(seatsGrid, 0, seatsGrid.getChildren().size());
    }

    private void onCLickActionSeat(MouseEvent e, Flight flight) {
        Node source = (Node) e.getTarget();

        if (source instanceof GridPane) {
            Seat tempSeat = flight.getPlane().getSeat()[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)];
            if (tempSeat.getSeatState()) {
                dController.alert(Route.ERROR, "Seat is already taken, try other seat");
            } else {
                char letter = tempSeat.getSeatLetter();
                int num = tempSeat.getSeatNumber();
                seatValue.setText(String.valueOf(letter) + num);
                clearChildrenStyle(seatsGrid, flight);
                source.setStyle("-fx-background-color: rgb(100,100,250); -fx-background-radius: 5px;");
                setPrice(GridPane.getColumnIndex(source), GridPane.getRowIndex(source), flight);
                selectSeat = flight.getPlane().getSeat()[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)];
            }

        } else {
            source = source.getParent().getParent();
            Seat tempSeat = flight.getPlane().getSeat()[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)];
            if (tempSeat.getSeatState()) {
                dController.alert(Route.ERROR, "Seat is already taken, try other seat");
            } else {
                char letter = tempSeat.getSeatLetter();
                int num = tempSeat.getSeatNumber();
                seatValue.setText(String.valueOf(letter) + num);
                clearChildrenStyle(seatsGrid, flight);
                source.setStyle("-fx-background-color: rgb(100,100,250); -fx-background-radius: 5px;");
                setPrice(GridPane.getColumnIndex(source), GridPane.getRowIndex(source), flight);
                selectSeat = flight.getPlane().getSeat()[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)];
            }
        }
    }

    private void setPrice(int x, int y, Flight flight) {
        Ticket t = new Ticket(flight, flight.getPlane().getSeat()[y][x], 0);
        t.calculatePrice((int) flight.getDuration());
        int price = t.getFlightPrice();
        ticketPrice.setText("" + price);
        if (flight.getPlane().getSeat()[y][x].isEconomic()) {
            tickedType.setValue("Economic");
            meal.setDisable(true);
        } else {
            tickedType.setValue("Premium");
            meal.setDisable(false);
        }

    }

    private void clearChildrenStyle(GridPane gp, Flight flight) {
        for (int i = 0; i < gp.getChildren().size(); i++) {
            Seat tempSeat = flight.getPlane().getSeat()[GridPane.getRowIndex(gp.getChildren().get(i))][GridPane
                    .getColumnIndex(gp.getChildren().get(i))];
            if (tempSeat.getSeatState()) {
                gp.getChildren().get(i).setStyle(
                        "-fx-background-color: rgb(179, 176, 176); -fx-border-color: gray; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            } else {
                gp.getChildren().get(i)
                        .setStyle("-fx-background-color: none; -fx-border-color: gray; -fx-border-radius: 5px;");
            }
        }

    }

    private void boxStyle(GridPane gP, int n, int i) {
        if (n < i) {
            GridPane.setFillHeight(gP.getChildren().get(n), true);
            GridPane.setFillWidth(gP.getChildren().get(n), true);
            GridPane.setHgrow(gP.getChildren().get(n), Priority.ALWAYS);
            GridPane.setVgrow(gP.getChildren().get(n), Priority.ALWAYS);
            GridPane.setHalignment(gP.getChildren().get(n), HPos.CENTER);
            GridPane.setValignment(gP.getChildren().get(n), VPos.CENTER);
            n++;
            boxStyle(gP, n, i);
        }
    }

    // Luggage Creation

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
    private JFXComboBox<SpecialLuggageType> cbType;

    @FXML
    private JFXTextField txtCharge;

    @FXML
    public void cancelModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    public void saveLuggage(ActionEvent event) {
        if (luggageValidation()) {
            if (cbType.getValue() == SpecialLuggageType.STANDARD) {
                Luggage tempLuggage = new Luggage(Integer.parseInt(txtHeight.getText()),
                        Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtWeight.getText()));
                luggages.add(tempLuggage);
                initializeTableView();
            } else {
                SpecialLuggage tempSpecialLuggage = new SpecialLuggage(Integer.parseInt(txtHeight.getText()),
                        Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtWeight.getText()),
                        cbType.getValue().name());
                luggages.add((Luggage) tempSpecialLuggage);
                initializeTableView();
            }
            updateLuggageToTrip(trip);
            calculateTripPrice();
            txtTotalPrice.setText("Total:         $" + tripPrice);
            modal.close();
            dController.alert(Route.SUCCESS, "Luggage added to trip");
        } else {
            dController.alert(Route.ERROR, "Fill all fields");
        }
    }

    @FXML
    public void newLuggage(ActionEvent event) throws IOException {
        Stage stage = dController.loadModal(Route.LUGAGGE_MODAL, this);
        setModal(stage);
        cbTypeData();
        stage.show();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    private boolean luggageValidation() {
        if (txtHeight.getText().equals("") || txtHeight.getText().equals("") || txtWeight.getText().equals("")
                || cbType.getValue() == null) {
            return false;
        } else {
            return true;
        }
    }

    private void cbTypeData() {
        cbType.getItems().addAll(SpecialLuggageType.values());
    }

    // Trip info panel

    @FXML
    private Label txtFlightId;

    @FXML
    private Label txtDate;
    @FXML
    private Label txtPriceflight;

    @FXML
    private Label txtPriceLuggage;

    @FXML
    private Label txtSelctedSeat;

    @FXML
    private Label txtTotalPrice;

    @FXML
    private Label txtTo;

    private void calculateTripPrice() {
        int calculate = 0;
        if (ticket != null) {
            txtDate.setText("Date: " + ticket.getFlight().getArrivalDate());
            txtPriceflight.setText("Flight:        $" + ticket.getFlightPrice());
            calculate += ticket.getFlightPrice();
        }
        int lugPrice = 0;
        for (int i = 0; i < luggages.size(); i++) {
            lugPrice += luggages.get(i).getLuggagePrice();
            calculate += luggages.get(i).getLuggagePrice();
        }
        txtPriceLuggage.setText("Luggage: $" + lugPrice);
        tripPrice = calculate;
    }

    private void updateLuggageToTrip(Trip trip) {
        if (trip != null) {
            for (int i = 0; i < luggages.size(); i++) {
                if (i == 0) {
                    trip.setRootLuggage(luggages.get(i));
                } else {
                    trip.addLuggage(luggages.get(i));
                }
            }
        }

    }

    /// Trip creation

    @FXML
    void buy(ActionEvent event) throws IOException {
        if (tripValidation()) {

            if (isUpdateMode) {
                modalUpdate.close();

            } else {
                String id = "T" + airport.getLogged().getTrips().size() + "-" + ticket.getFlight().getId();
                trip = new Trip((id), ticket, selectSeat);
                idTrip.setText("Trip Id:" + "value " + id);

                trip = new Trip(id, ticket, selectSeat);
                for (int i = 0; i < luggages.size(); i++) {
                    airport.getLogged().addLuggage(luggages.get(i), trip);
                }
                if (previousSeat != null) {
                    previousSeat.setSeatState(false);
                }
                selectSeat.setSeatState(true);
                airport.getLogged().addTrip(trip);
                trip.getTicket().getFlight().setCostumer(airport.getLogged());
                dController.loadView(Route.NEW_TRIP);
                dController.alert(Route.SUCCESS, "Trip booked");
                selectedFlight = null;
                selectSeat = null;
                trip = null;
            }
            luggages.clear();
            airport.saveData();
        } else {
            dController.alert(Route.ERROR, "Select a seat and try again");
        }
    }

    private boolean tripValidation() {
        if (ticket == null || selectSeat == null) {
            return false;
        } else {
            return true;
        }
    }

    // Load previous trip data
    public void prepareEdition(Trip trip, Stage modal) {
        this.ticket = trip.getTicket();
        this.trip = trip;
        this.selectedFlight = trip.getTicket().getFlight();
        this.selectSeat = trip.getTicket().getFligthSeat();
        previousSeat = trip.getTicket().getFligthSeat();
        this.tripPrice = trip.getTripPrice();
        this.luggages = trip.getLuggages();
        isUpdateMode = true;
        modalUpdate = modal;
        updateValidation();
    }

    private void updateValidation() {
        if (isUpdateMode) {
            cancelBtn.setVisible(true);
            buyBtn.setText("Update");
        }
    }

    @FXML
    private Button buyBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancelModalUpdate(ActionEvent event) {
        modalUpdate.close();
    }

}
