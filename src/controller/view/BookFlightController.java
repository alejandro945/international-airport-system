package controller.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.*;

import controller.DashboardController;
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
import javafx.scene.layout.*;
import model.*;
import javafx.stage.Stage;
import route.Route;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

public class BookFlightController {

    private Airport airport;
    private DashboardController dController;

    private Stage modal;
    private Stage modalTicket;

    private Ticket ticket;
    private Trip trip;
    private Flight selectedFlight;
    private Seat selectSeat;
    private int tripPrice;
    private List<Luggage> luggages;


    public BookFlightController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
        luggages = new ArrayList<>();
    }

    public void initialize() {
        filterInfo.setText("");
        loadData();
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

    /*
     * @FXML private TableColumn<Flight, String> priceT;
     */
    @FXML
    private TableColumn<Flight, String> arriveT;

    @FXML
    void filter(ActionEvent event) {
        if (date.getValue() == null || destiny.getValue().equals("Countries")) {
            dController.geAirportController().createAlert("Please fill the country and start date field", Route.ERROR);
        } else {
            filterInfo.setText(
                    "Flights to " + destiny.getValue() + " Starting date of search " + date.getValue().toString());
        }
    }

    void newTrip() throws IOException {
        Stage stageTicket = dController.loadModal(Route.TRIP_MODAL, this); /// Doble click vuelo
        this.modalTicket = stageTicket;
        stageTicket.show();
    }

    @FXML
    void placeSelection(ActionEvent event) {

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
        // arriveT.setCellValueFactory(new PropertyValueFactory<Flight,
        // Places>("destination"));

        // Call the string of the airport object
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
                        // TODO Auto-generated catch block
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
            txtFlightId.setText(selectedFlight.getId());
            txtTo.setText(selectedFlight.getDestination().name());
            txtSelctedSeat.setText(String.valueOf(selectSeat.getSeatLetter())+selectSeat.getSeatNumber()+"");
            System.out.println(String.valueOf(selectSeat.getSeatLetter()) + selectSeat.getSeatNumber());
            selectSeat.setSeatState(false);
            calculateTripPrice();
            txtTotalPrice.setText(tripPrice +"");
            String id =  airport.getLogged().countTrips() + ticket.getFlight().getId();
            trip = new Trip((id), ticket, selectSeat);
            idTrip.setText("value " + id );
            updateLuggageToTrip(trip);
            modalTicket.close();
            System.out.println(airport.getLogged().countTrips());
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
        // meal.setValue("Meals");
        meal.setItems(optionsComboBox1);

        // ---------------------

        List<String> types2 = new ArrayList<>();

        types2.add("Standard");
        types2.add("Premium");

        ObservableList<String> optionsComboBox2 = FXCollections.observableArrayList(types2);
        // tickedType.setValue("Type");
        tickedType.setItems(optionsComboBox2);
    }

    public void planeSize(Flight flight) {
        seatsGrid.getChildren().clear();
        int y = flight.getPlane().getCapacity()/6;
        // System.out.println(y + "Value");
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < 6; j++) {

                String letter = String.valueOf(flight.getPlane().getSeat()[i][j].getSeatLetter());
                int number = flight.getPlane().getSeat()[i][j].getSeatNumber();
                Label temp = new Label(letter + " " + number);

                GridPane tempGrid = new GridPane();

                // tempGrid.setHgap(5);
                tempGrid.setAlignment(Pos.CENTER);
                tempGrid.add(temp, 0, 0);
                tempGrid.setStyle(
                        "-fx-font-size:8; -fx-background-color: none; -fx-border-color: gray; -fx-border-radius: 5px;");
                
                        //System.out.println(Root.STYLE_GRID.getRoot());
                /* tempGrid.getStylesheets().add(Root.STYLE_GRID.getRoot());
                tempGrid.getStyleClass().add(".smallgrid"); */

                tempGrid.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        onCLickActionSeat(e, flight);
                    }
                });

                seatsGrid.add(tempGrid, j, i);
                // System.out.println(i + "-" + j);
            }
        }
        // addMiddleLine(y);
        // seatsGrid.setGridLinesVisible(true);
        boxStyle(seatsGrid, 0, seatsGrid.getChildren().size());
    }

    private void onCLickActionSeat(MouseEvent e, Flight flight) {
        // System.out.println("entra");
        Node source = (Node) e.getTarget();
        // System.out.println(e.getTarget().getClass());
        if (source instanceof GridPane) {
            // System.out.println(GridPane.getColumnIndex(source) + " " +
            // GridPane.getRowIndex(source));
            char letter = flight.getPlane().getSeat()[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)].getSeatLetter();
            int num = flight.getPlane().getSeat()[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)].getSeatNumber() ;
            //seatValue.setText(letter + "-" + num);
            //GridPane gp = (GridPane) source;
            seatValue.setText(String.valueOf(letter)+num);
            clearChildrenStyle(seatsGrid);
            source.setStyle("-fx-background-color: rgb(100,100,250); -fx-background-radius: 5px;");
            setPrice(GridPane.getColumnIndex(source), GridPane.getRowIndex(source), flight);
            selectSeat = flight.getPlane().getSeat()[GridPane.getColumnIndex(source)][GridPane.getRowIndex(source)];
        } else {
            source = source.getParent().getParent();
            char letter = flight.getPlane().getSeat()[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)].getSeatLetter();
            int num = flight.getPlane().getSeat()[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)].getSeatNumber() ;
            
            //seatValue.setText(letter + "-" + num);
            seatValue.setText(String.valueOf(letter)+num);
            clearChildrenStyle(seatsGrid);
            source.setStyle("-fx-background-color: rgb(100,100,250); -fx-background-radius: 5px;");
            setPrice(GridPane.getColumnIndex(source), GridPane.getRowIndex(source), flight);
            selectSeat = flight.getPlane().getSeat()[GridPane.getColumnIndex(source)][GridPane.getRowIndex(source)];
        }
    }

    private void setPrice(int x, int y, Flight flight) {
        // System.out.println(flight.getPlane().getSeat().length);
        int price = flight.getPlane().getSeat()[y][x].getPrice();
        ticketPrice.setText(price + "");
        if (flight.getPlane().getSeat()[y][x].isEconomic()) {
            tickedType.setValue("Economic");
            meal.setDisable(true);
        } else {
            tickedType.setValue("Premium");
            meal.setDisable(false);
        }

    }

    private void clearChildrenStyle(GridPane gp) {
        for (int i = 0; i < gp.getChildren().size(); i++) {
            gp.getChildren().get(i)
                    .setStyle("-fx-background-color: none; -fx-border-color: gray; -fx-border-radius: 5px;");
            // gp.setGridLinesVisible(true);
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
            // System.out.println("Estilo cuadro " + n);
            n++;
            boxStyle(gP, n, i);
        }
    }

    @FXML
    void clickSeatGrid(MouseEvent e) {

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
    private JFXComboBox<String> cbType;

    @FXML
    private JFXTextField txtCharge;

    @FXML
    void cancelModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    void editLuggage(ActionEvent event) {

    }

    @FXML
    void saveLuggage(ActionEvent event) {
        if (luggageValidation()) {
            if (cbType.getValue().equals("STANDARD")) {
                Luggage tempLuggage = new Luggage(Integer.parseInt(txtHeight.getText()),
                        Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtWeight.getText()));
                luggages.add(tempLuggage);
                initializeTableView();
            } else {
                SpecialLuggage tempSpecialLuggage = new SpecialLuggage(Integer.parseInt(txtHeight.getText()),
                        Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtWeight.getText()), cbType.getValue());
                luggages.add((Luggage)tempSpecialLuggage);
                initializeTableView();
            }
            updateLuggageToTrip(trip);
            calculateTripPrice();
            modal.close();
            dController.alert(Route.SUCCESS, "Luggage added to trip");
        } else {
            dController.alert(Route.ERROR, "Fill all fields");
        }
    }

    private void updateLuggageToTrip(Trip trip){
        if(trip != null){
            for(int i = 0; i < luggages.size(); i++){
                if(i == 0){
                    trip.setRootLuggage(luggages.get(i));
                }else{
                    trip.addLuggage(luggages.get(i));
                }
            }
        }
    }

    @FXML
    void newLuggage(ActionEvent event) throws IOException {
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
        List<String> types = new ArrayList<>();
        int i = 0;

        do {
            if (SpecialLuggageType.values()[i].name() != null) {
                types.add(SpecialLuggageType.values()[i].name());
                i++;
            }

        } while (i < SpecialLuggageType.values().length);

        ObservableList<String> optionsComboBox1 = FXCollections.observableArrayList(types);
        cbType.setItems(optionsComboBox1);

    }

    // Trip info panel

    @FXML
    private Label txtFlightId;

    @FXML
    private Label txtSelctedSeat;

    @FXML
    private Label txtTotalPrice;

    @FXML
    private Label txtTo;

    private void calculateTripPrice(){
        int calculate = 0;

        if(ticket != null ){
            calculate += ticket.getFlightPrice();
        } 

        for(int i = 0; i < luggages.size(); i++){
            calculate += luggages.get(i).getLuggagePrice();
        }

        tripPrice = calculate;
    }

}
