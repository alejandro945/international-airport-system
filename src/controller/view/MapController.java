package controller.view;

import java.net.URL;
import java.util.ResourceBundle;

import controller.DashboardController;
import javafx.fxml.FXML;
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

public class MapController implements Initializable {

    private DashboardController dController;
    private Airport airport;
    private int nodeIndex;

    public MapController(DashboardController dController, Airport airport) {
        this.dController = dController;
        this.airport = airport;
    }

    public DashboardController getDController() {
        return dController;
    }

    @FXML
    private Pane paneMap;
    @FXML
    private ImageView map;

    public void setAircraft(Flight flight, int index) {
        double advance = (flight.getJourney() * flight.getProgress()) / 100;
        Node plane = paneMap.getChildren().get(index);
        plane.setVisible(true);
        double render = 0;
        if (flight.getOrigin().getYPosition() < flight.getDestination().getYPosition()) {
            render = (flight.getSlope() * -1);
        } else {
            render = flight.getSlope();
        }
        plane.setLayoutX(advance + flight.getOrigin().getXPosition());
        plane.setLayoutY((render * advance) + flight.getOrigin().getYPosition());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Flight flight : airport.getFlights()) {
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
    }

}
