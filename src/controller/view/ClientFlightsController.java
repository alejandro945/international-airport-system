package controller.view;

import controller.DashboardController;
import controller.card.FlightCardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Flight;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFlightsController implements Initializable {
    @FXML
    private HBox hboxFlights;

    private final DashboardController dController;

    public ClientFlightsController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    void showNewFlight(ActionEvent event) throws IOException {
        dController.loadView(Route.NEW_TRIP);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
         * for (Flight flight : client.getFlights()) { try { addCard(flight); } catch
         * (IOException e) { e.printStackTrace(); } }
         */
    }

    public void addCard(Flight flight) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cards/flight.fxml"));
        FlightCardController controller = new FlightCardController(flight);
        fxmlLoader.setController(controller);
        Pane pane = fxmlLoader.load();
        controller.setData();
        hboxFlights.getChildren().add(pane);
    }
}
