package controller.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import controller.DashboardController;
import controller.node.NodeFlightController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Airline;
import model.Airport;
import model.Flight;
import route.Route;

public class ActiveFlightsController implements Initializable {

    @FXML
    private JFXTextField txtCode;

    @FXML
    private VBox activeBox;

    private DashboardController dController;
    private Airport airport;

    public ActiveFlightsController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @FXML
    public void searchFlight(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            for (Flight flight : airport.getFlights()) {
                try {
                    addNode(flight, flight.getAirline());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public void addNode(Flight flight, Airline airline) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Route.NODE_FLIGHT.getRoute()));
        NodeFlightController controller = new NodeFlightController();
        fxmlLoader.setController(controller);
        Pane pane = fxmlLoader.load();
        controller.getData(flight, airline);
        activeBox.getChildren().add(pane);
    }
}
