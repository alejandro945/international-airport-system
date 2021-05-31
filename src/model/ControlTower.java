package model;

import java.util.ArrayList;
import java.util.List;

public class ControlTower {
    private List<Flight> flights;

    public ControlTower() {
        flights = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

}
