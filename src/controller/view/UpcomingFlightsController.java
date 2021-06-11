package controller.view;

import com.jfoenix.controls.JFXTextField;
import controller.Constant;
import controller.DashboardController;
import controller.node.NodeUpcomingController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Airport;
import model.Flight;
import model.FlightState;
import route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpcomingFlightsController implements Initializable{

    @FXML
    private JFXTextField txtCode;

    @FXML
    private VBox activeBox;

    private DashboardController dController;
    private Airport airport;

    public UpcomingFlightsController(DashboardController dController,Airport airport) {
        this.dController = dController;
        this.airport = airport;
    }

    @FXML
    public void searchFlight(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Flight flight : airport.getFlights()) {
            if(flight.getFlightStatus()!=FlightState.AIRBORNE && flight.getFlightStatus()!=FlightState.DONE)
            try {
                addNode(flight);
            } catch (IOException e) {
               dController.alert(Route.ERROR, Constant.IOEXCEPTION);
            }
        }
    }
    public void addNode(Flight flight) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Route.NODE_UPCOMING_FLIGHT.getRoute()));
        NodeUpcomingController controller = new NodeUpcomingController();
        fxmlLoader.setController(controller);
        Pane pane = fxmlLoader.load();
        controller.getData(flight);
        activeBox.getChildren().add(pane);
    }

}
