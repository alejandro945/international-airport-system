package controller.view;

import controller.DashboardController;
import controller.card.FlightCardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Costumer;
import model.Trip;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFlightsController implements Initializable {
    @FXML
    private HBox hboxFlights;

    private final DashboardController dController;
    private Costumer client;

    public ClientFlightsController(DashboardController dController) {
        client = new Costumer();
        this.dController = dController;
    }

    @FXML
    void showNewFlight(ActionEvent event) throws IOException {
        dController.loadView(Route.NEW_TRIP);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Trip flight : client.getTrips()) {
            try {
                addCard(flight);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void addCard(Trip flight) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Route.CARD.getRoute()));
        FlightCardController controller = new FlightCardController(flight);
        fxmlLoader.setController(controller);
        Pane pane = fxmlLoader.load();
        controller.setData();
        hboxFlights.getChildren().add(pane);
    }
}
