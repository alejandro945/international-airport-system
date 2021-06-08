package controller.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.Airport;
import model.Flight;
import model.FlightState;
import route.Route;
import thread.MapThread;
import controller.node.NodeFlightController;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

public class ActiveFlightsController implements Initializable {
    private int nodeIndex;
    @FXML
    private JFXTextField txtCode;

    @FXML
    private VBox activeBox;

    private DashboardController dController;
    private Airport airport;

    @FXML
    private Pane paneMap;
    @FXML
    private ImageView map;

    public ActiveFlightsController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    public DashboardController getDController() {
        return dController;
    }

    @FXML
    public void searchFlight(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeBox.getChildren().clear();
        for (Flight flight : airport.getFlights()) {
            if (flight.getFlightStatus() == FlightState.AIRBORNE) {
                try {
                    addNode(flight);
                    initMap(flight);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addNode(Flight flight) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Route.NODE_FLIGHT.getRoute()));
        NodeFlightController controller = new NodeFlightController(dController);
        fxmlLoader.setController(controller);
        Pane pane = fxmlLoader.load();
        controller.getData(flight);
        activeBox.getChildren().add(pane);
    }

    public void initMap(Flight flight) {
        if (flight.getFlightStatus() == FlightState.AIRBORNE) {
            double xOrigin = flight.getOrigin().getXPosition();
            double yOrigin = flight.getOrigin().getYPosition();
            double xDestination = flight.getDestination().getXPosition();
            double yDestination = flight.getDestination().getYPosition();
            flight.setJourney(xDestination - xOrigin);
            flight.setSlope((yDestination - yOrigin) / (flight.getJourney()));
            ImageView plane = new ImageView(new Image("file:" + Route.AIRCRAFT.getRoute()));
            Line newLine = new Line(xOrigin + 15, yOrigin + 15, xDestination + 15, yDestination + 15);
            newLine.getStyleClass().add("shape");
            if (xOrigin > xDestination) {
                plane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            } else {
                plane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            }
            plane.setFitHeight(36);
            plane.setFitWidth(37);
            plane.setVisible(false);
            paneMap.getChildren().add(newLine);
            paneMap.getChildren().add(plane);
            nodeIndex += 2;
            MapThread mt = new MapThread(flight, this, nodeIndex);
            mt.start();
        }
    }

    public void setAircraft(Flight flight, int index) {
        double advance = (flight.getJourney() * flight.getProgress()) / 100;
        Node plane = paneMap.getChildren().get(index);
        plane.setVisible(true);
        double render = flight.getSlope();
        plane.setLayoutX(advance + flight.getOrigin().getXPosition());
        plane.setLayoutY((render * advance) + flight.getOrigin().getYPosition());
    }
}
