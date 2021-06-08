package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Migration implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Flight> flights;

    public Migration() {
        flights = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

}
