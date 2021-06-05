package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
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

    public BookFlightController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
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
    private TableView<?> luggageTbl;

    @FXML
    private TableColumn<?, ?> widthCol;

    @FXML
    private TableColumn<?, ?> heightCol;

    @FXML
    private TableColumn<?, ?> WeightCol;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> actionsCol;

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

    @FXML
    void newLuggage(ActionEvent event) throws IOException {
        Stage stage = dController.loadModal(Route.LUGAGGE_MODAL, this);
        setModal(stage);
        stage.show();
    }

    void newTrip() throws IOException {
        Stage stageTicket = dController.loadModal(Route.TRIP_MODAL, this); /// Doble click vuelo
        this.modalTicket = stageTicket;
        stageTicket.show();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    void cancelModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    void editLuggage(ActionEvent event) {

    }

    @FXML
    void saveLuggage(ActionEvent event) {

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
                    try {
                        newTrip();
                        Place.setText("To: " + temp.getDestination().toString());
                        Id.setText("ID: " + temp.getId());
                        tickedType.setDisable(true);
                        Place.setDisable(true);
                        Id.setDisable(true);
                        modalTicketDataLoad();
                        planeSize(temp);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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
        int y = flight.getPlane().getCapacity() / 6;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < 6; j++) {
                String letter = getLetter(j);
                int number = i + 1;
                Label temp = new Label(letter + " " + number);

                GridPane tempGrid = new GridPane();

                // tempGrid.setHgap(5);
                tempGrid.setStyle("-fx-font-size:8px;");
                tempGrid.setAlignment(Pos.CENTER);
                tempGrid.add(temp, 0, 0);
                tempGrid.setStyle("-fx-border-color: gray;");


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
        //addMiddleLine(y);
        // seatsGrid.setGridLinesVisible(true);
        boxStyle(seatsGrid, 0, seatsGrid.getChildren().size());
    }
    private void onCLickActionSeat(MouseEvent e, Flight flight){
        //System.out.println("entra");
        Node source = (Node) e.getTarget();
        //System.out.println(e.getTarget().getClass());
        if(source instanceof GridPane){
            //System.out.println(GridPane.getColumnIndex(source) + " " + GridPane.getRowIndex(source));
            String letter = getLetter(GridPane.getColumnIndex(source));
            int num = GridPane.getRowIndex(source) + 1;
            seatValue.setText(letter + "-" + num);
            clearChildrenStyle(seatsGrid);
            source.setStyle("-fx-background-color: rgb(250,100,100);");
            setPrice(GridPane.getColumnIndex(source), GridPane.getRowIndex(source), flight);
        }else{
            source = source.getParent().getParent();
            //System.out.println(GridPane.getColumnIndex(source) + " " + GridPane.getRowIndex(source));
            String letter = getLetter(GridPane.getColumnIndex(source));
            int num = GridPane.getRowIndex(source) + 1;
            seatValue.setText(letter + "-" + num);
            setPrice(GridPane.getColumnIndex(source), GridPane.getRowIndex(source), flight);
        }
    }

    private void setPrice(int x, int y, Flight flight){
        //System.out.println(flight.getPlane().getSeat().length);
        int price = flight.getPlane().getSeat()[y][x].getPrice();
        ticketPrice.setText(price+"");
        if(flight.getPlane().getSeat()[y][x].isEconomic()){
            tickedType.setValue("Economic");
            meal.setDisable(true);
        } else {
            tickedType.setValue("Premium");
            meal.setDisable(false);
        }
        

    }

    private void clearChildrenStyle(GridPane gp){
        for (int i = 0; i< gp.getChildren().size() ; i++){
            gp.getChildren().get(i).setStyle("-fx-background-color: none; -fx-border-color: gray;");
            //gp.setGridLinesVisible(true);
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

    private void addMiddleLine(int length) {
        for (int i = 0; i < length; i++) {
            GridPane child = new GridPane();
            child.setStyle("-fx-border-color: white;");
            seatsGrid.add(child, 3, length);
        }

    }

    @FXML
    void clickSeatGrid(MouseEvent e) {
        
    }

    private String getLetter(int num) {
        switch (num) {
            case 0:
                return "A";

            case 1:
                return "B";

            case 2:
                return "C";

            case 3:
                return "D";

            case 4:
                return "E";

            case 5:
                return "F";

            default:
                return "E";

        }
    }
}
